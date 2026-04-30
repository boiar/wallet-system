package org.boiar.walletmanagement.user.service;

import java.util.UUID;
import org.boiar.walletmanagement.user.request.ChangePasswordRequest;
import org.boiar.walletmanagement.user.request.ProfileUpdateRequest;
import org.boiar.walletmanagement.user.response.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  void updateProfileInfo(ProfileUpdateRequest req, UUID userId);

  void changePassword(ChangePasswordRequest req, UUID userId);

  void deactivateAccount(UUID userId);

  void reactivateAccount(UUID userId);

  void deleteAccount(UUID userId);

  UserProfileResponse getUserById(UUID userId);

  UserDetails loadUserByEmail(String email);
}
