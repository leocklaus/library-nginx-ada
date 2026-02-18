package io.github.leocklaus.sistema_biblioteca.loan.infrastructure.mapper;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.ClientId;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.Loan;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanId;
import io.github.leocklaus.sistema_biblioteca.loan.infrastructure.persistence.LoanEntity;

public final class LoanMapper {

    private LoanMapper() {}

    public static LoanEntity toEntity(Loan loan) {
        return new LoanEntity(
                loan.getId().value(),
                loan.getLibraryId().value(),
                loan.getClientId().value(),
                loan.getBookId().value(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

    public static Loan toDomain(LoanEntity entity) {
        return Loan.rehydrate(
                new LoanId(entity.getId()),
                new LibraryId(entity.getLibraryId()),
                new ClientId(entity.getClientId()),
                new BookId(entity.getBookId()),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getReturnDate(),
                entity.getStatus()
        );
    }
}
