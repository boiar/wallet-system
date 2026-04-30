package org.boiar.walletmanagement.core.lang;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
@RequiredArgsConstructor
public class LocaleConfig {

  private static final String LANG_HEADER = "lang";

  private final I18nProperties i18nProperties;
  private final FolderBasedMessageSource folderBasedMessageSource;

  @Bean
  public LocaleResolver localeResolver() {
    return new LocaleResolver() {
      @Override
      public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getHeader(LANG_HEADER);
        if (lang == null || !i18nProperties.getSupportedLangs().contains(lang.toLowerCase())) {
          return Locale.forLanguageTag(i18nProperties.getDefaultLang());
        }

        return Locale.forLanguageTag(lang.toLowerCase());
      }

      @Override
      public void setLocale(
          HttpServletRequest request, HttpServletResponse response, Locale locale) {}
    };
  }

  @Bean
  public MessageSource messageSource() {
    return folderBasedMessageSource;
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(folderBasedMessageSource);
    return bean;
  }
}
