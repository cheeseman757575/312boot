package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

//обработчик аутеннтификц. пользов. -> используется в конфиг классе безопасности WebSecurityConfig
@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler { //интерфейс определяюзий логику приложения после аутен. пользователя
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.обрабатывает его успешную аутентификацию
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());//получение ролей пользователя,
        // преобразует GrantedAuthority, полученный .getAuthorities() в список пользователей. authentication - содержит аутентифицированного
        // пользователя и его данные, такие как имя пользователя, пароль и роли.
        if (roles.contains("USER")) { // пользователь содержит роль "ROLE_USER"
            httpServletResponse.sendRedirect("/user"); // да, направляем на "/user"
        } else {
            httpServletResponse.sendRedirect("/"); //нет, направляем на "/"
        }
    }
}