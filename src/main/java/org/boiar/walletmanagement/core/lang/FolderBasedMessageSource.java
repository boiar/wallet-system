package org.boiar.walletmanagement.core.lang;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FolderBasedMessageSource implements MessageSource {

  private final I18nProperties i18nProperties;

  // Cache per language
  private final Map<String, MessageSource> cache = new ConcurrentHashMap<>();

  @Override
  public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
    try {
      return getMessage(code, args, locale);
    } catch (NoSuchMessageException e) {
      return defaultMessage;
    }
  }

  @Override
  public String getMessage(String code, Object[] args, Locale locale)
      throws NoSuchMessageException {
    String lang = locale.getLanguage();
    MessageSource source = cache.computeIfAbsent(lang, this::buildMessageSource);
    return source.getMessage(code, args, locale);
  }

  @Override
  public String getMessage(MessageSourceResolvable resolvable, Locale locale)
      throws NoSuchMessageException {
    String lang = locale.getLanguage();
    MessageSource source = cache.computeIfAbsent(lang, this::buildMessageSource);
    return source.getMessage(resolvable, locale);
  }

  private MessageSource buildMessageSource(String lang) {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

    String resolvedLang = folderExists(lang) ? lang : i18nProperties.getDefaultLang();

    String[] basenames =
        i18nProperties.getModules().stream()
            .map(module -> i18nProperties.getBasePath() + resolvedLang + "/" + module)
            .toArray(String[]::new);

    source.setBasenames(basenames);
    source.setDefaultEncoding("UTF-8");
    source.setCacheSeconds(i18nProperties.getCacheSeconds());
    source.setFallbackToSystemLocale(false);
    return source;
  }

  private boolean folderExists(String lang) {
    String path = i18nProperties.getBasePath() + lang + "/";
    try {
      Resource resource = path.startsWith("classpath")
              ? new ClassPathResource(path.replace("classpath:", ""))
              : new FileSystemResource(path.replace("file:", ""));
      return resource.exists();
    } catch (Exception e) {
      return false;
    }
  }
}
