package io.github.leocklaus.sistema_biblioteca.user.api.controller;

import io.github.leocklaus.sistema_biblioteca.user.api.dto.LibrarianResult;
import io.github.leocklaus.sistema_biblioteca.user.api.dto.RegisterLibrarianRequest;
import io.github.leocklaus.sistema_biblioteca.user.api.dto.RegisterLibrarianResponse;
import io.github.leocklaus.sistema_biblioteca.user.application.usecase.CreateLibrarianUseCase;
import io.github.leocklaus.sistema_biblioteca.user.application.usecase.GetlibrarianUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/libraries/{libraryId}/librarians")
public class LibrarianController {

    private final CreateLibrarianUseCase createLibrarianUseCase;
    private final GetlibrarianUseCase getlibrarianUseCase;

    public LibrarianController(CreateLibrarianUseCase createLibrarianUseCase, GetlibrarianUseCase getlibrarianUseCase) {
        this.createLibrarianUseCase = createLibrarianUseCase;
        this.getlibrarianUseCase = getlibrarianUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterLibrarianResponse> register(
            @PathVariable UUID libraryId,
            @RequestBody RegisterLibrarianRequest request
    ) {
        RegisterLibrarianResponse result = createLibrarianUseCase.register(
                libraryId,
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterLibrarianResponse(result.librarianId()));
    }

    @GetMapping
    public ResponseEntity<List<LibrarianResult>> getAll(
            @PathVariable UUID libraryId
    ) {
        List<LibrarianResult> result =
                getlibrarianUseCase.execute(libraryId);

        return ResponseEntity.ok(result);
    }
}
