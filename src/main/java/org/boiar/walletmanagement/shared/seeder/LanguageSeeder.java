package org.boiar.walletmanagement.shared.seeder;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.shared.entity.Language;
import org.boiar.walletmanagement.shared.repository.CurrencyRepository;
import org.boiar.walletmanagement.shared.repository.LanguageRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LanguageSeeder implements ApplicationRunner {

    private final LanguageRepository languageRepo;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (languageRepo.count() > 0) {
            return;
        }

        List<Language> languageList = List.of(
                Language.builder()
                        .code("en")
                        .name(Map.of(
                                "en", "English",
                                "ar", "الإنجليزية"
                        ))
                        .rtl(false)
                        .build(),

                Language.builder()
                        .code("ar")
                        .name(Map.of(
                                "en", "Arabic",
                                "ar", "العربية"
                        ))
                        .rtl(true)
                        .build()
        );

        languageRepo.saveAll(languageList);
    }
}
