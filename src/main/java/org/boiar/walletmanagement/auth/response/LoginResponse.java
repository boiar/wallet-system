package org.boiar.walletmanagement.auth.response;

import lombok.Builder;
import lombok.Data;
import org.boiar.walletmanagement.user.response.UserProfileResponse;

@Data
@Builder
public class LoginResponse {
  private UserProfileResponse user;
  private String accessToken;
  private String refreshToken;
}
