package org.boiar.walletmanagement.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@RequiredArgsConstructor
public class ThymeleafConfig {

    private final MessageSource messageSource;

    @Value("${spring.thymeleaf.cache}")
    private boolean thymeleafCache;

    @Value("${spring.thymeleaf.prefix:classpath:templates/}")
    private String templatePrefix;

    @Bean
    public ITemplateResolver emailTemplateResolver() {

        // resolver settings
        if (templatePrefix.startsWith("file:")) {
            // Dev — reads from mounted ./src/main/resources/templates/
            FileTemplateResolver resolver = new FileTemplateResolver();
            resolver.setPrefix(templatePrefix.replace("file:", ""));  // → /app/templates/mail/
            resolver.setSuffix(".html");
            resolver.setTemplateMode(TemplateMode.HTML);
            resolver.setCharacterEncoding("UTF-8");
            resolver.setCacheable(false);
            resolver.setOrder(1);
            return resolver;
        }

        // Prod — reads from JAR classpath (original behavior)
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(thymeleafCache);
        resolver.setOrder(1);
        return resolver;
    }


    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(emailTemplateResolver());

        // use locale
        engine.setTemplateEngineMessageSource(messageSource);
        return engine;
    }
}
