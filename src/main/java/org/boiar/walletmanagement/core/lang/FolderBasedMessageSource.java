package org.boiar.walletmanagement.core.lang;

import org.springframework.core.io.ClassPathResource;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        String lang = locale.getLanguage();
        MessageSource source = cache.computeIfAbsent(lang, this::buildMessageSource);
        return source.getMessage(code, args, locale);
    }



    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        String lang = locale.getLanguage();
        MessageSource source = cache.computeIfAbsent(lang, this::buildMessageSource);
        return source.getMessage(resolvable, locale);
    }

    private MessageSource buildMessageSource(String lang) {
        ReloadableResourceBundleMessageSource source =
                new ReloadableResourceBundleMessageSource();

        String resolvedLang = folderExists(lang) ? lang : i18nProperties.getDefaultLang();

        String[] basenames = i18nProperties.getModules().stream()
                .map(module -> "classpath:messages/" + resolvedLang + "/" + module)
                .toArray(String[]::new);

        source.setBasenames(basenames);
        source.setDefaultEncoding("UTF-8");
        source.setCacheSeconds(3600);
        source.setFallbackToSystemLocale(false);
        return source;
    }


    private boolean folderExists(String lang) {
        Resource resource = new ClassPathResource("messages/" + lang + "/");
        return resource.exists();
    }

}
