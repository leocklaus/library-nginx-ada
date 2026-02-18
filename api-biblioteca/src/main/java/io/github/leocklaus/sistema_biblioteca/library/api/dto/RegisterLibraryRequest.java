package io.github.leocklaus.sistema_biblioteca.library.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterLibraryRequest(
        @NotBlank String libraryName,
        @Email String libraryEmail,
        @Email String adminEmail,
        @NotBlank String adminPassword
) {
}
