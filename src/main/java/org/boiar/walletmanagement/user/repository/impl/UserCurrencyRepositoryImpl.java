package org.boiar.walletmanagement.user.repository.impl;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.user.entity.UserCurrency;
import org.boiar.walletmanagement.user.repository.UserCurrencyRepository;
import org.boiar.walletmanagement.user.repository.jpa.UserCurrencyRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserCurrencyRepositoryImpl implements UserCurrencyRepository {

  private final UserCurrencyRepositoryJpa userCurrencyRepoJpa;

  @Override
  public List<UserCurrency> findByUserId(UUID userId) {
    return userCurrencyRepoJpa.findByUserId(userId);
  }

  @Override
  public Optional<UserCurrency> findByUserIdAndPrimaryTrue(UUID userId) {
    return userCurrencyRepoJpa.findByUserIdAndPrimaryTrue(userId);
  }

  @Override
  public boolean existsByUserIdAndCurrencyId(UUID userId, Long currencyId) {
    return userCurrencyRepoJpa.existsByUserIdAndCurrencyId(userId, currencyId);
  }

  @Override
  public void deleteByUserIdAndCurrencyId(UUID userId, Long currencyId) {
    userCurrencyRepoJpa.deleteByUserIdAndCurrencyId(userId, currencyId);
  }
}
