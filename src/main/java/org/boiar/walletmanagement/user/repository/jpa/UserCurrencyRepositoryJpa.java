package org.boiar.walletmanagement.user.repository.jpa;

import org.boiar.walletmanagement.user.entity.UserCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserCurrencyRepositoryJpa extends JpaRepository<UserCurrency, Long> {
    List<UserCurrency> findByUserId(UUID userId);
    Optional<UserCurrency> findByUserIdAndPrimaryTrue(UUID userId);
    boolean existsByUserIdAndCurrencyId(UUID userId, Long currencyId);
    void deleteByUserIdAndCurrencyId(UUID userId, Long currencyId);
}
