package org.boiar.walletmanagement.user.mapper;

import org.boiar.walletmanagement.auth.requests.RegisterRequest;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.request.ProfileUpdateRequest;
import org.boiar.walletmanagement.user.response.UserProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public void mergeUserInfo(final User user, final ProfileUpdateRequest request) {
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
  }

  public UserProfileResponse toUserProfileResponse(final User user) {
    return UserProfileResponse.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .phoneNumber(user.getPhone())
        .build();
  }

  public User toUserEntity(RegisterRequest request, String encodedPassword) {
    return User.builder()
        .firstName(request.f_name())
        .lastName(request.l_name())
        .email(request.email())
        .phone(request.phone())
        .password(encodedPassword)
        .emailVerified(false)
        .enabled(true)
        .build();
  }
}
