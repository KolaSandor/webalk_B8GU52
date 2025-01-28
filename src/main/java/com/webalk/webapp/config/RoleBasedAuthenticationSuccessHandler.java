package com.webalk.webapp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // A felhasználó szerepköreinek lekérése
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            // Ha admin, akkor az admin.html-re irányítjuk
            response.sendRedirect("/admin/dashboard");
        } else if (roles.contains("ROLE_USER")) {
            // Ha user, akkor a user.html-re irányítjuk
            response.sendRedirect("/books");
        } else {
            // Egyéb esetben egy alapértelmezett oldalra irányítjuk
            response.sendRedirect("/default");
        }
    }
}
