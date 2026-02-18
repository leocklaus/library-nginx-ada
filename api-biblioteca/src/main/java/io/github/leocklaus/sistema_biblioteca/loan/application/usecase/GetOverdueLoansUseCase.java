package io.github.leocklaus.sistema_biblioteca.loan.application.usecase;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.loan.api.dto.LoanResult;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.Loan;
import io.github.leocklaus.sistema_biblioteca.loan.domain.repository.LoanRepository;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetOverdueLoansUseCase {

    private final LoanRepository loanRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public GetOverdueLoansUseCase(LoanRepository loanRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.loanRepository = loanRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public List<LoanResult> get(UUID libraryId) {

        AuthenticatedUser user = authenticatedUserProvider.get();
        LibraryId libraryIdEntity = new LibraryId(libraryId);

        if (!user.canAccessLibrary(libraryIdEntity)) {
            throw new ForbiddenOperationException("Sem permissão para ver atrasos");
        }

        return loanRepository.findOverdueByLibraryId(libraryId)
                .stream()
                .map(this::toResult)
                .toList();
    }

    private LoanResult toResult(Loan loan) {
        return new LoanResult(
                loan.getId().value(),
                loan.getBookId().value(),
                loan.getClientId().value(),
                loan.getLibraryId().value(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus().name()
        );
    }
}
