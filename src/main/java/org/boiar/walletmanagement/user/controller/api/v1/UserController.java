package org.boiar.walletmanagement.user.controller.api.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.request.ChangePasswordRequest;
import org.boiar.walletmanagement.user.request.ProfileUpdateRequest;
import org.boiar.walletmanagement.user.response.UserProfileResponse;
import org.boiar.walletmanagement.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API V1")
public class UserController {

  private final UserService userService;

  @GetMapping("/profile")
  @ResponseStatus(code = HttpStatus.OK)
  public UserProfileResponse getProfile(final Authentication principal) {
    return this.userService.getUserById(getUserId(principal));
  }

  @PatchMapping("update-profile")
  @ResponseStatus(code = HttpStatus.OK)
  public void updateProfile(
      @RequestBody @Valid final ProfileUpdateRequest request, final Authentication principal) {

    this.userService.updateProfileInfo(request, getUserId(principal));
  }

  @PostMapping("update-password")
  @ResponseStatus(code = HttpStatus.OK)
  public void changePassword(
      @RequestBody @Valid final ChangePasswordRequest request, final Authentication principal) {
    this.userService.changePassword(request, getUserId(principal));
  }

  @PatchMapping("profile/deactivate")
  @ResponseStatus(code = HttpStatus.OK)
  public void deactivateAccount(final Authentication principal) {
    this.userService.deactivateAccount(getUserId(principal));
  }

  @PatchMapping("/profile/reactivate")
  @ResponseStatus(code = HttpStatus.OK)
  public void reactivateAccount(final Authentication principal) {
    this.userService.reactivateAccount(getUserId(principal));
  }

  @DeleteMapping("/delete")
  @ResponseStatus(code = HttpStatus.OK)
  public void deleteAccount(final Authentication principal) {
    this.userService.deleteAccount(getUserId(principal));
  }

  private UUID getUserId(final Authentication authentication) {
    return ((User) Objects.requireNonNull(authentication.getPrincipal())).getId();
  }
}
