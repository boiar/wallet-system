package org.boiar.walletmanagement.user.repository;

import java.util.Optional;
import java.util.UUID;
import org.boiar.walletmanagement.user.entity.User;

public interface UserRepository {
  boolean existsByEmailIgnoreCase(String email);

  boolean existsByPhone(String phoneNumber);

  boolean existsByEmail(String email);

  Optional<User> findByEmailIgnoreCase(String email);

  Optional<User> findByEmail(String email);

  Optional<User> findById(UUID userId);

  void save(User userObj);
}
