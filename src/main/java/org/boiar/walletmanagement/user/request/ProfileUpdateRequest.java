package org.boiar.walletmanagement.user.request;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUpdateRequest {
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
}
