package org.boiar.walletmanagement.user.repository;

import org.boiar.walletmanagement.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findById(UUID userId);

    void save(User userObj);
}
