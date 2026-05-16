package org.boiar.walletmanagement.shared.repository;

import org.boiar.walletmanagement.shared.entity.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {

    Optional<Currency> findByCode(String code);

    List<Currency> findByIsActiveTrue();

    List<Currency> findAll();

    long count();

    void saveAll(List<Currency> currencyList);
}
