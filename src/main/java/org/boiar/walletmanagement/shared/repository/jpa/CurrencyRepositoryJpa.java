package org.boiar.walletmanagement.shared.repository.jpa;

import org.boiar.walletmanagement.shared.entity.Currency;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepositoryJpa extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCode(String code);

    List<Currency> findByActiveTrue();


}
