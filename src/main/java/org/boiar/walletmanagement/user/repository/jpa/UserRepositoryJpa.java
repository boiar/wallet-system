package org.boiar.walletmanagement.user.repository.jpa;

import java.util.Optional;
import java.util.UUID;
import org.boiar.walletmanagement.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<User, UUID> {
  boolean existsByEmailIgnoreCase(String email);

  boolean existsByPhone(String phoneNumber);

  boolean existsByEmail(String email);

  Optional<User> findByEmailIgnoreCase(String email);

  Optional<User> findByEmail(String email);
}
