package org.boiar.walletmanagement.user.repository.impl;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.repository.UserRepository;
import org.boiar.walletmanagement.user.repository.jpa.UserRepositoryJpa;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserRepositoryJpa userRepositoryJpa;

  @Override
  public boolean existsByEmailIgnoreCase(String email) {
    return this.userRepositoryJpa.existsByEmailIgnoreCase(email);
  }

  @Override
  public boolean existsByPhone(String phoneNumber) {
    return this.userRepositoryJpa.existsByPhone(phoneNumber);
  }

  @Override
  public boolean existsByEmail(String email) {
    return this.userRepositoryJpa.existsByEmail(email);
  }

  @Override
  public Optional<User> findByEmailIgnoreCase(String email) {
    return this.userRepositoryJpa.findByEmailIgnoreCase(email);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return this.userRepositoryJpa.findByEmail(email);
  }

  @Override
  public Optional<User> findById(UUID userId) {
    return this.userRepositoryJpa.findById(userId);
  }

  @Override
  public void save(User userObj) {
    this.userRepositoryJpa.save(userObj);
  }
}
