package org.boiar.walletmanagement.auth.mapper;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.auth.response.LoginResponse;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

  private final UserMapper userMapper;

  public LoginResponse toLoginResponse(User user, String accessToken, String refreshToken) {
    return LoginResponse.builder()
        .user(userMapper.toUserProfileResponse(user))
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }
}
