package io.github.leocklaus.sistema_biblioteca.loan.api.dto;

import io.github.leocklaus.sistema_biblioteca.loan.domain.model.Loan;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanStatus;

import java.time.LocalDate;
import java.util.UUID;

public record CreateLoanResponse(
        UUID loanId,
        UUID clientId,
        UUID bookId,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate,
        LoanStatus status
) {
    public static CreateLoanResponse from(Loan loan) {
        return new CreateLoanResponse(
                loan.getId().value(),
                loan.getClientId().value(),
                loan.getBookId().value(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }
}
