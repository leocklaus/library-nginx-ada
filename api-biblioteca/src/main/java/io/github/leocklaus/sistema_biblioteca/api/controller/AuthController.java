package io.github.leocklaus.sistema_biblioteca.api.controller;

import io.github.leocklaus.sistema_biblioteca.api.dto.LoginRequest;
import io.github.leocklaus.sistema_biblioteca.api.dto.LoginResponse;
import io.github.leocklaus.sistema_biblioteca.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {

        String token = authService.login(
                request.email(),
                request.password()
        );

        return ResponseEntity.ok(new LoginResponse(token));
    }
}

