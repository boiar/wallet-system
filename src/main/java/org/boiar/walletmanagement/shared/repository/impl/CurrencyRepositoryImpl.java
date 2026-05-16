package org.boiar.walletmanagement.shared.repository.impl;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.shared.repository.CurrencyRepository;
import org.boiar.walletmanagement.shared.repository.jpa.CurrencyRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final CurrencyRepositoryJpa currencyRepoJpa;

    @Override
    public Optional<Currency> findByCode(String code) {
        return currencyRepoJpa.findByCode(code);
    }

    @Override
    public List<Currency> findByIsActiveTrue() {
        return currencyRepoJpa.findByActiveTrue();
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepoJpa.findAll();
    }

    @Override
    public long count() {
        return currencyRepoJpa.count();
    }

    @Override
    public void saveAll(List<Currency> currencyList) {
        currencyRepoJpa.saveAll(currencyList);
    }

}
