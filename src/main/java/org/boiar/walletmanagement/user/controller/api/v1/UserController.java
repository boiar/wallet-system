package org.boiar.walletmanagement.user.controller.api.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.core.lang.LocaleHelper;
import org.boiar.walletmanagement.core.response.ApiResponse;
import org.boiar.walletmanagement.core.security.annotation.CurrentUser;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.request.ChangePasswordRequest;
import org.boiar.walletmanagement.user.request.ProfileUpdateRequest;
import org.boiar.walletmanagement.user.response.UserProfileResponse;
import org.boiar.walletmanagement.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API V1")
public class UserController {

  private final UserService userService;
  private final LocaleHelper localeHelper;

  @GetMapping("/profile")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(@CurrentUser User user) {
    UserProfileResponse response =  this.userService.getUserById(user.getId());

    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(localeHelper.get("general.fetched.successfully"), response));
  }

  @PatchMapping("update-profile")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<ApiResponse<Object>> updateProfile(
      @RequestBody
      @Valid final ProfileUpdateRequest request,
      @CurrentUser User user ) {

    this.userService.updateProfileInfo(request, user.getId());

    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(localeHelper.get("general.updated.successfully")));
  }

  @PostMapping("update-password")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<ApiResponse<Object>> changePassword(
      @RequestBody
      @Valid
      final ChangePasswordRequest request,
      @CurrentUser User user) {

    this.userService.changePassword(request, user.getId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(localeHelper.get("general.updated.successfully")));
  }

  @PatchMapping("profile/deactivate")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<ApiResponse<Object>> deactivateAccount(@CurrentUser User user) {
    this.userService.deactivateAccount(user.getId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(localeHelper.get("general.updated.successfully")));
  }

  @PatchMapping("/profile/reactivate")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<ApiResponse<Object>> reactivateAccount(@CurrentUser User user) {

    this.userService.reactivateAccount(user.getId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(localeHelper.get("general.updated.successfully")));
  }

  @DeleteMapping("/delete")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<ApiResponse<Object>> deleteAccount(@CurrentUser User user) {

    this.userService.deleteAccount(user.getId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(localeHelper.get("general.deleted.successfully")));
  }

}
