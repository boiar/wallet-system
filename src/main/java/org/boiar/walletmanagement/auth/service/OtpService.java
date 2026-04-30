package org.boiar.walletmanagement.auth.service;

import org.boiar.walletmanagement.auth.enums.OtpTypeEnum;
import org.boiar.walletmanagement.auth.requests.*;
import org.boiar.walletmanagement.user.entity.User;

public interface OtpService {
  void verifyOtp(VerifyOtpRequest request);

  void resendOtp(ResendOtpRequest request);

  void sendOtp(User user, OtpTypeEnum type);
}
