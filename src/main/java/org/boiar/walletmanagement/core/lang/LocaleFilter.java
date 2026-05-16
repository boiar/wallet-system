package org.boiar.walletmanagement.core.lang;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

@Component
@RequiredArgsConstructor
public class LocaleFilter extends OncePerRequestFilter {

  @Value("${app.i18n.default-lang}")
  private String defaultLang;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String lang = request.getParameter("lang");
    Locale locale = (lang != null && !lang.isBlank())
            ? Locale.forLanguageTag(lang)
            : Locale.forLanguageTag(defaultLang);

    LocaleContextHolder.setLocale(locale);

      try {
        filterChain.doFilter(request, response);
      } finally {
        LocaleContextHolder.resetLocaleContext();
      }
  }
}
