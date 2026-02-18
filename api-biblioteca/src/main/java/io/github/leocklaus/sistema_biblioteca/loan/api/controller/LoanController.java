package io.github.leocklaus.sistema_biblioteca.loan.api.controller;

import io.github.leocklaus.sistema_biblioteca.loan.api.dto.CreateLoanRequest;
import io.github.leocklaus.sistema_biblioteca.loan.api.dto.CreateLoanResponse;
import io.github.leocklaus.sistema_biblioteca.loan.api.dto.LoanResult;
import io.github.leocklaus.sistema_biblioteca.loan.application.usecase.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/libraries/{libraryId}/loans")
public class LoanController {

    private final CreateLoanUseCase createLoanUseCase;
    private final ReturnLoanUseCase returnLoanUseCase;
    private final GetLoansByClientUseCase getLoansByClientUseCase;
    private final GetLoansByLibraryUseCase getLoansByLibraryUseCase;
    private final GetOverdueLoansUseCase getOverdueLoansUseCase;

    public LoanController(CreateLoanUseCase createLoanUseCase, ReturnLoanUseCase returnLoanUseCase, GetLoansByClientUseCase getLoansByClientUseCase, GetLoansByLibraryUseCase getLoansByLibraryUseCase, GetOverdueLoansUseCase getOverdueLoansUseCase) {
        this.createLoanUseCase = createLoanUseCase;
        this.returnLoanUseCase = returnLoanUseCase;
        this.getLoansByClientUseCase = getLoansByClientUseCase;
        this.getLoansByLibraryUseCase = getLoansByLibraryUseCase;
        this.getOverdueLoansUseCase = getOverdueLoansUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateLoanResponse> createLoan(
            @PathVariable UUID libraryId,
            @RequestBody CreateLoanRequest request
    ) {
        CreateLoanResponse response = createLoanUseCase
                .execute(libraryId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{loanId}/return")
    public ResponseEntity<CreateLoanResponse> returnLoan(
            @PathVariable UUID libraryId,
            @PathVariable UUID loanId
    ) {
        CreateLoanResponse response =
                returnLoanUseCase.execute(libraryId, loanId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LoanResult>> getAll(
            @PathVariable UUID libraryId
    ) {
        return ResponseEntity.ok(
                getLoansByLibraryUseCase.get(libraryId)
        );
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<List<LoanResult>> getByClient(
            @PathVariable UUID libraryId,
            @PathVariable UUID clientId
    ) {
        return ResponseEntity.ok(
                getLoansByClientUseCase.get(libraryId, clientId)
        );
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResult>> getOverdue(
            @PathVariable UUID libraryId
    ) {
        return ResponseEntity.ok(
                getOverdueLoansUseCase.get(libraryId)
        );
    }
}

