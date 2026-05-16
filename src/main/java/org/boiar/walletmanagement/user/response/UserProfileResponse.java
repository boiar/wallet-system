package org.boiar.walletmanagement.user.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String profilePictureUrl;
}
