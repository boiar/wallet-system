package org.boiar.walletmanagement.core.lang;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.i18n")
public class I18nProperties {
    private List<String> modules        = List.of("general", "validation");
    private List<String> supportedLangs = List.of("en");
    private String       defaultLang    = "en";
}
