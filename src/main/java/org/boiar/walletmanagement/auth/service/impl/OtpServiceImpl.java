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
import org.boiar.walletmanagement.notifications.service.EmailNotificationService;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.mapper.UserMapper;
import org.boiar.walletmanagement.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

  private final UserRepository userRepo;
  private final OtpRepository otpRepo;
  private final LocaleHelper localeHelper;
  private final UserMapper userMapper;
  private final OtpMapper otpMapper;
  private final EmailNotificationService emailNotificationService;

  @Value("${app.otp.expiration-minutes:5}")
  private int otpExpirationMinutes;

  @Value("${app.i18n.default-lang}")
  private String defaultLang;



  @Override
  public void verifyOtp(VerifyOtpRequest request) {
    User user =
        userRepo
            .findByEmail(request.email())
            .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

    if(user.isEmailVerified()) {
      throw new AuthException(AuthErrorCode.ACCOUNT_ALREADY_VERIFIED);
    }

    Otp otp =
        otpRepo
            .findTopByUserEmailAndTypeAndUsedFalseOrderByCreatedDateDesc(
                request.email(), request.type())
            .orElseThrow(() -> new AuthException(AuthErrorCode.OTP_NOT_FOUND));

    if (otp.isExpired()) throw new AuthException(AuthErrorCode.OTP_EXPIRED);

    if (!otp.getCode().equals(request.otp())) throw new AuthException(AuthErrorCode.OTP_INVALID);

    // Mark OTP Used
    otp.setUsed(true);
    otpRepo.save(otp);
    // verify user
    user.setEmailVerified(true);
    userRepo.save(user);

  }

  @Override
  @Transactional
  public void resendOtp(ResendOtpRequest request) {
    User user =
        userRepo
            .findByEmail(request.email())
            .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

    otpRepo.deleteAllByUserEmailAndType(request.email(), request.type());

    String code = generateOtpCode();
    Otp otpObj = otpMapper.toOtpEntity(user, code, request.type(), otpExpirationMinutes);

    otpRepo.save(otpObj);
    emailNotificationService.resendOtp(user.getEmail(), user.getFullName(), code, defaultLang);
  }

  public void sendOtp(User user, OtpTypeEnum type) {
    String code = generateOtpCode();

    Otp otpObj = otpMapper.toOtpEntity(user, code, type, otpExpirationMinutes);

    otpRepo.save(otpObj);
    emailNotificationService.sendOtpRegister(user.getEmail(), user.getFullName(), code, defaultLang);
  }

  private String generateOtpCode() {
    // 6-digit secure otp
    SecureRandom Random = new SecureRandom();
    return String.format("%06d", Random.nextInt(1_000_000));
  }


}
