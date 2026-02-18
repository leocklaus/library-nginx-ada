package io.github.leocklaus.sistema_biblioteca.loan.api.dto;

import java.util.UUID;

public record CreateLoanRequest(
        UUID clientId,
        UUID bookId,
        int daysToReturn
) {}
