package io.github.leocklaus.sistema_biblioteca.security.context;

import io.github.leocklaus.sistema_biblioteca.security.jwt.JwtUserDetails;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.Role;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.UserId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SpringSecurityAuthenticatedUserProvider implements AuthenticatedUserProvider {

    @Override
    public AuthenticatedUser get() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

        UserId userId = new UserId(userDetails.getUserId());

        Set<Role> roles = userDetails.getRoles().stream()
                .map(Role::fromString)
                .collect(Collectors.toSet());

        return new AuthenticatedUser(userId, roles);
    }
}
