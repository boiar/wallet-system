package org.boiar.walletmanagement.auth.mapper;

import java.time.LocalDateTime;
import org.boiar.walletmanagement.auth.entity.RefreshToken;
import org.boiar.walletmanagement.auth.response.RefreshTokenResponse;
import org.boiar.walletmanagement.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenMapper {

  public RefreshToken toRefreshTokenEntity(User user, String token, int refreshTokenExpirationDays) {
    return RefreshToken.builder()
        .user(user)
        .token(token)
        .revoked(false)
        .expiresAt(LocalDateTime.now().plusDays(refreshTokenExpirationDays))
        .build();
  }

  public RefreshTokenResponse toRefreshTokenResponse (String refreshToken, String accessToken, Long expiresIn) {
    return RefreshTokenResponse.builder()
            .refreshToken(refreshToken)
            .accessToken(accessToken)
            .tokenType("Bearer")
            .expiresIn(expiresIn)
            .build();
  }

}
