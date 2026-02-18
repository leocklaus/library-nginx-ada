package io.github.leocklaus.sistema_biblioteca.inventory.application.usecase;

import io.github.leocklaus.sistema_biblioteca.book.application.usecase.GetBookByLibraryIdAndBookIdUseCase;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.inventory.api.dto.AddInventoryRequest;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.model.Inventory;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.repository.InventoryRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddInventoryUseCase {

    private final InventoryRepository inventoryRepository;
    private final GetBookByLibraryIdAndBookIdUseCase getBookByLibraryIdAndBookIdUseCase;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public AddInventoryUseCase(InventoryRepository inventoryRepository, GetBookByLibraryIdAndBookIdUseCase getBookByLibraryIdAndBookIdUseCase, AuthenticatedUserProvider authenticatedUserProvider) {
        this.inventoryRepository = inventoryRepository;
        this.getBookByLibraryIdAndBookIdUseCase = getBookByLibraryIdAndBookIdUseCase;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public void execute(
            UUID libraryId,
            UUID bookId,
            AddInventoryRequest request) {

        AuthenticatedUser user = authenticatedUserProvider.get();

        LibraryId libraryIdEntity = new LibraryId(libraryId);
        BookId bookIdEntity = new BookId(bookId);

        if (!user.canAccessLibrary(libraryIdEntity)) {
            throw new ForbiddenOperationException("Sem permissão para gerenciar inventário");
        }

        Book book = getBookByLibraryIdAndBookIdUseCase.execute(libraryIdEntity, bookIdEntity);

        Inventory inventory = inventoryRepository
                .findByLibraryIdAndBookId(libraryIdEntity, bookIdEntity)
                .orElseGet(() -> Inventory.create(
                        libraryIdEntity,
                        bookIdEntity,
                        0
                ));

        inventory.addQuantity(request.quantity());

        inventoryRepository.save(inventory);
    }
}
