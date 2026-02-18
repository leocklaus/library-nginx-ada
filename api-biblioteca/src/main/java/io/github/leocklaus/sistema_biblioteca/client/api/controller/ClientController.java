package io.github.leocklaus.sistema_biblioteca.client.api.controller;

import io.github.leocklaus.sistema_biblioteca.client.api.dto.ClientResponse;
import io.github.leocklaus.sistema_biblioteca.client.api.dto.RegisterClientRequest;
import io.github.leocklaus.sistema_biblioteca.client.api.dto.RegisterClientResponse;
import io.github.leocklaus.sistema_biblioteca.client.application.usecase.FindClientsByLibraryUseCase;
import io.github.leocklaus.sistema_biblioteca.client.application.usecase.RegisterClientUseCase;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/libraries/{libraryId}/clients")
public class ClientController {

    private final RegisterClientUseCase registerClientUseCase;
    private final FindClientsByLibraryUseCase findClientsByLibraryUseCase;

    public ClientController(RegisterClientUseCase registerClientUseCase, FindClientsByLibraryUseCase findClientsByLibraryUseCase) {
        this.registerClientUseCase = registerClientUseCase;
        this.findClientsByLibraryUseCase = findClientsByLibraryUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> list(
            @PathVariable UUID libraryId
    ) {
        List<ClientResponse> clients =
                findClientsByLibraryUseCase.execute(new LibraryId(libraryId));

        return ResponseEntity.ok(clients);
    }


    @PostMapping
    public ResponseEntity<RegisterClientResponse> register(
            @PathVariable UUID libraryId,
            @RequestBody RegisterClientRequest request
    ) {
        RegisterClientResponse result = registerClientUseCase
                .execute(request, libraryId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterClientResponse(result.clientId()));
    }
}
