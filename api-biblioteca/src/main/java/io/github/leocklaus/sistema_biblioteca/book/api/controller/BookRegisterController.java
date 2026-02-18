package io.github.leocklaus.sistema_biblioteca.book.api.controller;

import io.github.leocklaus.sistema_biblioteca.book.api.dto.RegisterBookRequest;
import io.github.leocklaus.sistema_biblioteca.book.api.dto.RegisterBookResponse;
import io.github.leocklaus.sistema_biblioteca.book.application.usecase.RegisterBookUseCase;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/libraries/{libraryId}/books")
public class BookRegisterController {

    private final RegisterBookUseCase registerBookUseCase;

    public BookRegisterController(RegisterBookUseCase registerBookUseCase) {
        this.registerBookUseCase = registerBookUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterBookResponse> register(
            @PathVariable UUID libraryId,
            @RequestBody RegisterBookRequest request
    ) {

        RegisterBookResponse response =
                registerBookUseCase.register(libraryId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
