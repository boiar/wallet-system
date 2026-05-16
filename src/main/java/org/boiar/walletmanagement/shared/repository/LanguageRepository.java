package org.boiar.walletmanagement.shared.repository;

import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.shared.entity.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository {

    Optional<Language> findByCode(String code);

    List<Language> findByIsActiveTrue();

    List<Language> findAll();

    long count();

    void saveAll(List<Language> languages);
}
