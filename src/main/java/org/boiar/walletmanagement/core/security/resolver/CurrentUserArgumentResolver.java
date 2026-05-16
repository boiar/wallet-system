package org.boiar.walletmanagement.core.security.resolver;

import lombok.extern.slf4j.Slf4j;
import org.boiar.walletmanagement.core.security.annotation.CurrentUser;
import org.boiar.walletmanagement.user.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Slf4j
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) &&
                (parameter.getParameterType().equals(User.class) ||
                 parameter.getParameterType().equals(UUID.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null  || !auth.isAuthenticated()) {
            log.warn("No authenticated user found in security context");
            return null;
        }

        User user = (User) auth.getPrincipal();
        if (parameter.getParameterType().equals(UUID.class)) {
            return user.getId();
        }

        return user;
    }
}
