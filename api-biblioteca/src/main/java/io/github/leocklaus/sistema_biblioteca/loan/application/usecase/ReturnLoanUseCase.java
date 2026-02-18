package io.github.leocklaus.sistema_biblioteca.loan.application.usecase;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.repository.InventoryRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.loan.api.dto.CreateLoanResponse;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.Loan;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanId;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanStatus;
import io.github.leocklaus.sistema_biblioteca.loan.domain.repository.LoanRepository;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class ReturnLoanUseCase {

    private final LoanRepository loanRepository;
    private final InventoryRepository inventoryRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public ReturnLoanUseCase(LoanRepository loanRepository, InventoryRepository inventoryRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.loanRepository = loanRepository;
        this.inventoryRepository = inventoryRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public CreateLoanResponse execute(UUID libraryId, UUID loanId) {

        AuthenticatedUser user = authenticatedUserProvider.get();
        LibraryId libId = new LibraryId(libraryId);

        if (!user.isAdminOf(libId) && !user.isLibrarianOf(libId)) {
            throw new ForbiddenOperationException("Sem permissão para devolver empréstimos desta biblioteca");
        }

        Loan loan = loanRepository.findById(new LoanId(loanId))
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (!loan.getLibraryId().equals(libId)) {
            throw new IllegalArgumentException("Empréstimo não pertence a esta biblioteca");
        }

        if (loan.getStatus() != LoanStatus.EMPRESTADO) {
            throw new IllegalStateException("Este empréstimo já foi devolvido");
        }

        LocalDate returnDate = LocalDate.now();
        loan.markReturned(returnDate);

        loanRepository.save(loan);

        BookId bookId = loan.getBookId();
        inventoryRepository.increment(libId, bookId);

        return CreateLoanResponse.from(loan);
    }
}
