package org.boiar.walletmanagement.auth.service;

import org.boiar.walletmanagement.auth.requests.*;
import org.boiar.walletmanagement.auth.response.LoginResponse;
import org.boiar.walletmanagement.auth.response.RefreshTokenResponse;

public interface AuthService {
  void register(RegisterRequest request);

  LoginResponse login(LoginRequest request);

  RefreshTokenResponse refresh(RefreshTokenRequest request);

  void logout(LogoutRequest request);

  void forgetPassword(ForgetPasswordRequest request);

  void resetPassword(ResetPasswordRequest request);
}
