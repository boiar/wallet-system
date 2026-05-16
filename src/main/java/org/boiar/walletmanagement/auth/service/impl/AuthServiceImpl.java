package org.boiar.walletmanagement.auth.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.auth.entity.Otp;
import org.boiar.walletmanagement.auth.entity.RefreshToken;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.auth.exception.AuthErrorCode;
import org.boiar.walletmanagement.auth.exception.AuthException;
import org.boiar.walletmanagement.auth.mapper.AuthMapper;
import org.boiar.walletmanagement.auth.mapper.RefreshTokenMapper;
import org.boiar.walletmanagement.auth.repository.OtpRepository;
import org.boiar.walletmanagement.auth.repository.RefreshTokenRepository;
import org.boiar.walletmanagement.auth.requests.*;
import org.boiar.walletmanagement.auth.response.LoginResponse;
import org.boiar.walletmanagement.auth.response.RefreshTokenResponse;
import org.boiar.walletmanagement.auth.service.AuthService;
import org.boiar.walletmanagement.auth.service.OtpService;
import org.boiar.walletmanagement.core.lang.LocaleHelper;
import org.boiar.walletmanagement.core.security.service.JwtService;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.mapper.UserMapper;
import org.boiar.walletmanagement.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepo;
  private final OtpRepository otpRepo;
  private final RefreshTokenRepository refreshTokenRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final OtpService otpService;
  private final LocaleHelper localeHelper;
  private final UserMapper userMapper;
  private final RefreshTokenMapper refreshTokenMapper;
  private final AuthMapper authMapper;

  @Value("${app.refresh-token.expiration-days:7}")
  private int refreshTokenExpirationDays;

  @Value("${jwt.expiration}")
  private Long expTime;

  @Override
  @Transactional
  public void register(RegisterRequest request) {
    if (userRepo.existsByEmail(request.email()))
      throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);

    String encodedPassword = passwordEncoder.encode(request.password());
    User userObj = userMapper.toUserEntity(request, encodedPassword);

    userRepo.save(userObj);
    otpService.sendOtp(userObj, OtpTypeEnum.REGISTER);
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    User user =
        userRepo
            .findByEmail(request.email())
            .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

    if (!passwordEncoder.matches(request.password(), user.getPassword()))
      throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);

    if (!user.isVerified()) throw new AuthException(AuthErrorCode.ACCOUNT_NOT_VERIFIED);

    if (!user.isEnabled()) throw new AuthException(AuthErrorCode.ACCOUNT_DISABLED);

    String accessToken  = jwtService.generateToken(user.getId());
    String refreshToken = createRefreshToken(user);

    return authMapper.toLoginResponse(user, accessToken, refreshToken);
  }

  @Override
  @Transactional
  public void forgetPassword(ForgetPasswordRequest request) {
    User user = userRepo.findByEmail(request.email()).orElse(null);

    if (user != null) {
      otpRepo.deleteAllByUserEmailAndType(request.email(), OtpTypeEnum.FORGET_PASSWORD);

      otpService.sendOtp(user, OtpTypeEnum.FORGET_PASSWORD);
    }

  }

  @Override
  @Transactional
  public void resetPassword(ResetPasswordRequest request) {
    User user =
        userRepo
            .findByEmail(request.email())
            .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

    Otp otp =
        otpRepo
            .findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedDateDesc(
                request.email(), OtpTypeEnum.FORGET_PASSWORD)
            .orElseThrow(() -> new AuthException(AuthErrorCode.OTP_INVALID));

    if (otp.isExpired()) throw new AuthException(AuthErrorCode.OTP_EXPIRED);

    if (!otp.getCode().equals(request.otp())) throw new AuthException(AuthErrorCode.OTP_INVALID);

    // mark otp used
    otp.setUsed(true);
    otpRepo.save(otp);

    user.setPassword(passwordEncoder.encode(request.password()));
    userRepo.save(user);

    // revoke all old refresh tokens
    refreshTokenRepo.revokeAllByUserId(user.getId());
  }

  @Override
  public RefreshTokenResponse refresh(RefreshTokenRequest request) {
    RefreshToken refreshToken =
        refreshTokenRepo
            .findByToken(request.refreshToken())
            .orElseThrow(() -> new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

    if (refreshToken.isRevoked()) throw new AuthException(AuthErrorCode.REFRESH_TOKEN_REVOKED);

    if (refreshToken.isExpired()) {
      refreshToken.setRevoked(true);
      refreshTokenRepo.save(refreshToken);
      throw new AuthException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
    }

    // Revoke old token — rotate refresh token
    refreshToken.setRevoked(true);
    refreshTokenRepo.save(refreshToken);

    String accessToken  = jwtService.generateToken(refreshToken.getUser().getId());
    String newRefreshToken = createRefreshToken(refreshToken.getUser());

    return refreshTokenMapper.toRefreshTokenResponse(newRefreshToken, accessToken, expTime);
  }

  @Override
  public void logout(LogoutRequest request) {

    RefreshToken refreshToken =
        refreshTokenRepo
            .findByToken(request.refreshToken())
            .orElseThrow(() -> new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

    // revoke old tokens
    refreshTokenRepo.revokeAllByUserId(refreshToken.getUser().getId());
  }

  private String createRefreshToken(User user) {
    String token = UUID.randomUUID().toString();

    RefreshToken refreshTokenObj =
        refreshTokenMapper.toRefreshTokenEntity(user, token, refreshTokenExpirationDays);
    refreshTokenRepo.save(refreshTokenObj);

    return token;
  }
}
