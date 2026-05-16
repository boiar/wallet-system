package org.boiar.walletmanagement.shared.repository.jpa;

import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.shared.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepositoryJpa extends JpaRepository<Language, Long> {

    Optional<Language> findByCode(String code);

    List<Language> findByActiveTrue();
}
