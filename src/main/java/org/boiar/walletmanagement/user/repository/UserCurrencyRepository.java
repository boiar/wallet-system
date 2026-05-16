package org.boiar.walletmanagement.user.repository;

import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.user.entity.UserCurrency;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCurrencyRepository {
    List<UserCurrency> findByUserId(UUID userId);
    Optional<UserCurrency> findByUserIdAndPrimaryTrue(UUID userId);
    boolean existsByUserIdAndCurrencyId(UUID userId, Long currencyId);
    void deleteByUserIdAndCurrencyId(UUID userId, Long currencyId);
}
