package io.github.leocklaus.sistema_biblioteca.loan.application.usecase;

import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.ClientId;
import io.github.leocklaus.sistema_biblioteca.client.domain.repository.ClientRepository;
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
public class GetLoansByClientUseCase {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public GetLoansByClientUseCase(LoanRepository loanRepository, ClientRepository clientRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public List<LoanResult> get(UUID libraryId, UUID clientId) {

        AuthenticatedUser user = authenticatedUserProvider.get();
        LibraryId libraryIdEntity = new LibraryId(libraryId);

        if (!user.canAccessLibrary(libraryIdEntity)) {
            throw new ForbiddenOperationException("Sem permissão para ver empréstimos");
        }

        Client client = clientRepository.findById(new ClientId(clientId))
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (!client.getLibraryId().value().equals(libraryId)) {
            throw new IllegalArgumentException("Cliente não pertence à biblioteca");
        }

        return loanRepository
                .findByLibraryIdAndClientId(libraryId, clientId)
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
