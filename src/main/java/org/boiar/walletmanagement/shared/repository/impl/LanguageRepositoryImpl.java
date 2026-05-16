package org.boiar.walletmanagement.shared.repository.impl;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.shared.entity.Language;
import org.boiar.walletmanagement.shared.repository.LanguageRepository;
import org.boiar.walletmanagement.shared.repository.jpa.LanguageRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LanguageRepositoryImpl implements LanguageRepository {

    private final LanguageRepositoryJpa languageRepoJpa;

    @Override
    public Optional<Language> findByCode(String code) {
        return languageRepoJpa.findByCode(code);
    }

    @Override
    public List<Language> findByIsActiveTrue() {
        return languageRepoJpa.findByActiveTrue();
    }

    @Override
    public List<Language> findAll() {
        return languageRepoJpa.findAll();
    }

    @Override
    public long count() {
        return languageRepoJpa.count();
    }

    @Override
    public void saveAll(List<Language> languages) {
        languageRepoJpa.saveAll(languages);
    }
}
