package org.boiar.walletmanagement.auth.mapper;

import java.time.LocalDateTime;
import org.boiar.walletmanagement.auth.entity.Otp;
import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class OtpMapper {

  public Otp toOtpEntity(User user, String code, OtpTypeEnum type, int otpExpirationMinutes) {
    return Otp.builder()
        .user(user)
        .code(code)
        .type(type)
        .used(false)
        .expiresAt(LocalDateTime.now().plusMinutes(otpExpirationMinutes))
        .build();
  }
}
