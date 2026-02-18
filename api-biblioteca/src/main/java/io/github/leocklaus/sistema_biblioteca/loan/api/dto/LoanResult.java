package io.github.leocklaus.sistema_biblioteca.loan.api.dto;

import java.time.LocalDate;
import java.util.UUID;

public record LoanResult(
        UUID loanId,
        UUID bookId,
        UUID clientId,
        UUID libraryId,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate,
        String status
) {}
