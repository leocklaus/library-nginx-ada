package io.github.leocklaus.sistema_biblioteca.library.api.controller;

import io.github.leocklaus.sistema_biblioteca.library.api.dto.LibraryResponse;
import io.github.leocklaus.sistema_biblioteca.library.api.dto.RegisterLibraryRequest;
import io.github.leocklaus.sistema_biblioteca.library.api.dto.RegisterLibraryResponse;
import io.github.leocklaus.sistema_biblioteca.library.application.command.RegisterLibraryCommand;
import io.github.leocklaus.sistema_biblioteca.library.application.result.RegisterLibraryResult;
import io.github.leocklaus.sistema_biblioteca.library.application.usecase.GetLibraryUseCase;
import io.github.leocklaus.sistema_biblioteca.library.application.usecase.RegisterLibraryUseCase;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/libraries")
public class LibraryController {
    private final RegisterLibraryUseCase service;
    private final GetLibraryUseCase getLibraryUseCase;

    public LibraryController(RegisterLibraryUseCase service, GetLibraryUseCase getLibraryUseCase) {
        this.service = service;
        this.getLibraryUseCase = getLibraryUseCase;
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<LibraryResponse> getById(@PathVariable UUID id) {

        Library library = getLibraryUseCase.getById(id);

        return ResponseEntity.ok(LibraryResponse.from(library));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<LibraryResponse>> getAll() {

        List<Library> libraries = getLibraryUseCase.getAll();

        return ResponseEntity.ok(
                libraries.stream()
                        .map(LibraryResponse::from)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<RegisterLibraryResponse> register(
            @Valid @RequestBody RegisterLibraryRequest request
    ) {

        RegisterLibraryResult result = service.execute(
                new RegisterLibraryCommand(
                        request.libraryName(),
                        request.libraryEmail(),
                        request.adminEmail(),
                        request.adminPassword()
                )
        );

        URI uri = URI.create("/libraries/" + result.libraryId());

        return ResponseEntity.created(uri).build();
    }
}
