package io.github.leocklaus.sistema_biblioteca.user.api.controller;

import io.github.leocklaus.sistema_biblioteca.user.api.dto.UserResponse;
import io.github.leocklaus.sistema_biblioteca.user.application.service.UserService;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {

        User user = service.getById(id);

        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/by-library/{libraryId}")
    public ResponseEntity<List<UserResponse>> getByLibrary(
            @PathVariable UUID libraryId
    ) {
        List<User> users = service.getByLibrary(libraryId);

        return ResponseEntity.ok(
                users.stream()
                        .map(UserResponse::from)
                        .toList()
        );
    }
}
