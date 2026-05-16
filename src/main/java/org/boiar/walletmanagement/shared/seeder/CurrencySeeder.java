package org.boiar.walletmanagement.shared.seeder;

import lombok.RequiredArgsConstructor;
import org.boiar.walletmanagement.shared.entity.Currency;
import org.boiar.walletmanagement.shared.repository.CurrencyRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CurrencySeeder implements ApplicationRunner {

    private final CurrencyRepository currencyRepo;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (currencyRepo.count() > 0) {
            return;
        }


        List<Currency> currencyList = List.of(
                Currency.builder()
                        .code("USD")
                        .symbol("$")
                        .name(Map.of(
                                "en", "US Dollar",
                                "ar", "دولار أمريكي"
                        ))
                        .build(),

                Currency.builder()
                        .code("EGP")
                        .symbol("E£")
                        .name(Map.of(
                                "en", "Egyptian Pound",
                                "ar", "جنيه مصري"
                        ))
                        .build(),

                Currency.builder()
                        .code("EUR")
                        .symbol("€")
                        .name(Map.of(
                                "en", "Euro",
                                "ar", "يورو"
                        ))
                        .build()
        );

        currencyRepo.saveAll(currencyList);
    }
}
