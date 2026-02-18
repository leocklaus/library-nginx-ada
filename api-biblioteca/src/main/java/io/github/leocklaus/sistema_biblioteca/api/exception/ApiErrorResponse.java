package io.github.leocklaus.sistema_biblioteca.api.exception;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        List<String> messages
) {
    public static ApiErrorResponse of(String message) {
        return new ApiErrorResponse(Instant.now(), List.of(message));
    }

    public static ApiErrorResponse of(List<String> messages) {
        return new ApiErrorResponse(Instant.now(), messages);
    }
}
