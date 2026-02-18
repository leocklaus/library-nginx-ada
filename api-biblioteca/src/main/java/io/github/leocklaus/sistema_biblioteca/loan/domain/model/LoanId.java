package io.github.leocklaus.sistema_biblioteca.loan.domain.model;

import java.util.Objects;
import java.util.UUID;

public record LoanId(UUID value) {
    public LoanId {
        Objects.requireNonNull(value, "LoanId não pode ser nulo");
    }

    public static LoanId newId() {
        return new LoanId(UUID.randomUUID());
    }
}
