package org.boiar.walletmanagement.user.repository.impl;

import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.repository.UserRepository;
import org.boiar.walletmanagement.user.repository.jpa.UserRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return this.userRepositoryJpa.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return this.userRepositoryJpa.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return this.userRepositoryJpa.findByEmailIgnoreCase(email);
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
