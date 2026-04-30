package org.boiar.walletmanagement.auth.service.impl;

import java.security.SecureRandom;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.auth.entity.Otp;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.auth.exception.AuthErrorCode;
import org.boiar.walletmanagement.auth.exception.AuthException;
import org.boiar.walletmanagement.auth.mapper.OtpMapper;
import org.boiar.walletmanagement.auth.repository.OtpRepository;
import org.boiar.walletmanagement.auth.requests.*;
import org.boiar.walletmanagement.auth.service.OtpService;
import org.boiar.walletmanagement.core.lang.LocaleHelper;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.mapper.UserMapper;
import org.boiar.walletmanagement.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

  private final UserRepository userRepo;
  private final OtpRepository otpRepo;
  private final LocaleHelper localeHelper;
  private final UserMapper userMapper;
  private final OtpMapper otpMapper;

  @Value("${app.otp.expiration-minutes:5}")
  private int otpExpirationMinutes;

  @Override
  public void verifyOtp(VerifyOtpRequest request) {
    User user =
        userRepo
            .findByEmail(request.email())
            .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

    Otp otp =
        otpRepo
            .findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(
                request.email(), request.type())
            .orElseThrow(() -> new AuthException(AuthErrorCode.OTP_NOT_FOUND));

    if (otp.isExpired()) throw new AuthException(AuthErrorCode.OTP_EXPIRED);

    if (!otp.getCode().equals(request.otp())) throw new AuthException(AuthErrorCode.OTP_INVALID);

    // Mark OTP Used
    otp.setUsed(true);
    otpRepo.save(otp);

    if (request.type() == OtpTypeEnum.REGISTER) {
      user.setEmailVerified(true);
      userRepo.save(user);
    }

    // return ApiResponse.success(localeHelper.get("auth.otp.verified"), null);

  }

  @Override
  public void resendOtp(ResendOtpRequest request) {
    User user =
        userRepo
            .findByEmail(request.email())
            .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

    otpRepo.deleteAllByUserEmailAndType(request.email(), request.type());

    sendOtp(user, request.type());

    // return ApiResponse.success(localeHelper.get("auth.otp.resent"), null);
  }

  public void sendOtp(User user, OtpTypeEnum type) {
    String code = generateOtpCode();

    Otp otpObj = otpMapper.toOtpEntity(user, code, type, otpExpirationMinutes);

    otpRepo.save(otpObj);

    // TODO: plug in your email/SMS service here
    // notificationService.sendOtp(user.getEmail(), code);
  }

  private String generateOtpCode() {
    // 6-digit secure otp
    SecureRandom Random = new SecureRandom();
    return String.format("%06d", Random.nextInt(1_000_000));
  }
}
