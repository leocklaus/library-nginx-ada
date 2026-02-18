package io.github.leocklaus.sistema_biblioteca.loan.application.usecase;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.ClientId;
import io.github.leocklaus.sistema_biblioteca.client.domain.repository.ClientRepository;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.repository.InventoryRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.loan.api.dto.CreateLoanRequest;
import io.github.leocklaus.sistema_biblioteca.loan.api.dto.CreateLoanResponse;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.Loan;
import io.github.leocklaus.sistema_biblioteca.loan.domain.repository.LoanRepository;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CreateLoanUseCase {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;
    private final InventoryRepository inventoryRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public CreateLoanUseCase(LoanRepository loanRepository, ClientRepository clientRepository, InventoryRepository inventoryRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
        this.inventoryRepository = inventoryRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public CreateLoanResponse execute(UUID libraryId, CreateLoanRequest request){
        AuthenticatedUser user = authenticatedUserProvider.get();

        LibraryId libraryIdEntity = new LibraryId(libraryId);

        if (!user.canAccessLibrary(libraryIdEntity)) {
            throw new ForbiddenOperationException("Sem permissão para criar empréstimo nesta biblioteca");
        }

        ClientId clientIdEntity = new ClientId(request.clientId());
        Client client = clientRepository.findById(clientIdEntity)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (!client.getLibraryId().value().equals(libraryId)) {
            throw new IllegalArgumentException("Cliente não pertence a esta biblioteca");
        }

        BookId bookId = new BookId(request.bookId());

        int available = inventoryRepository.getAvailableQuantity(libraryIdEntity, bookId);
        if (available <= 0) {
            throw new IllegalArgumentException("Livro sem exemplares disponíveis");
        }

        inventoryRepository.decrement(libraryIdEntity, bookId);

        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(request.daysToReturn());

        Loan loan = Loan.create(libraryIdEntity, clientIdEntity, bookId, loanDate, dueDate);
        loanRepository.save(loan);

        return CreateLoanResponse.from(loan);
    }
}
