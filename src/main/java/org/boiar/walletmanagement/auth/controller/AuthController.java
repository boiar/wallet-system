package org.boiar.walletmanagement.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.auth.requests.*;
import org.boiar.walletmanagement.auth.response.LoginResponse;
import org.boiar.walletmanagement.auth.response.RefreshTokenResponse;
import org.boiar.walletmanagement.auth.service.AuthService;
import org.boiar.walletmanagement.auth.service.OtpService;
import org.boiar.walletmanagement.core.lang.LocaleHelper;
import org.boiar.walletmanagement.core.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final OtpService otpService;
  private final LocaleHelper localeHelper;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
    authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(localeHelper.get("auth.register.success"), null));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest request) {

    return ResponseEntity.ok(
        ApiResponse.success(localeHelper.get("auth.login.success"), authService.login(request)));
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<ApiResponse<Void>> verifyOtp(
          @Valid @RequestBody VerifyOtpRequest request) {

    otpService.verifyOtp(request);
    return ResponseEntity.ok(ApiResponse.success(localeHelper.get("auth.login.success"),null));
  }

  @PostMapping("/resend-otp")
  public ResponseEntity<ApiResponse<Void>> resendOtp(
          @Valid @RequestBody ResendOtpRequest request) {

    otpService.resendOtp(request);
    return ResponseEntity.ok(ApiResponse.success(localeHelper.get("auth.otp.resent"),null));
  }

  @PostMapping("/forget-password")
  public ResponseEntity<ApiResponse<Void>> forgetPassword(
          @Valid @RequestBody ForgetPasswordRequest request) {

    authService.forgetPassword(request);
    return ResponseEntity.ok(ApiResponse.success(localeHelper.get("auth.forget.password.sent"),null));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPassword(
          @Valid @RequestBody ResetPasswordRequest request) {

    authService.resetPassword(request);
    return ResponseEntity.ok(ApiResponse.success(localeHelper.get("auth.password.reset.success"),null));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<RefreshTokenResponse>> refresh(
          @Valid @RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok( ApiResponse.success(localeHelper.get("auth.token.refreshed"), authService.refresh(request)));
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
          @Valid @RequestBody LogoutRequest request) {
    authService.logout(request);
    return ResponseEntity.ok(ApiResponse.success(localeHelper.get("auth.logout.success"), null));
  }




}
