package org.boiar.walletmanagement.notifications.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import java.io.InputStream;


@Slf4j
@Component
@RequiredArgsConstructor
public class MailTemplateBuilder {

    private final TemplateEngine templateEngine;

    @Value("${spring.application.name:Wallet Management}")
    private String appName;


    //  Build HTML email from Thymeleaf template
    public String build(String templateKey, Map<String, String> variables) {

      try {

          Locale currentLocale = LocaleContextHolder.getLocale();

          Context context = new Context(currentLocale);
          context.setVariable("appName", appName);
          context.setVariable("year", Year.now().getValue());

          if (variables != null) {
              variables.forEach(context::setVariable);
          }

          // thymeleaf process
          String templatePath = "mail/" + templateKey;
          return templateEngine.process(templatePath, context);

      } catch (Exception e) {
          log.error("Failed to process template {} : {}", templateKey, e.getMessage(), e);
          return buildFallback(templateKey, variables);
      }
    }




    private String buildFallback(String templateKey, Map<String, String> variables) {
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<h3>Notification: ").append(templateKey).append("</h3>");
        if (variables != null) {
            variables.forEach((k, v) ->
                    sb.append("<p><strong>").append(k).append(":</strong> ").append(v).append("</p>")
            );
        }
        sb.append("</body></html>");
        return sb.toString();
    }


}
