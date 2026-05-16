package org.boiar.walletmanagement.core.lang;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.i18n")
public class I18nProperties {

  private String basePath = "classpath:messages/";
  private List<String> modules;
  private List<String> supportedLangs;
  private String defaultLang;
  private int cacheSeconds = 0;
}
