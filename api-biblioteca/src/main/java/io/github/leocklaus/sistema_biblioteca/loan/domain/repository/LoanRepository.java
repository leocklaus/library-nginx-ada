package io.github.leocklaus.sistema_biblioteca.loan.domain.repository;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.Loan;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository {
    void save(Loan loan);

    Optional<Loan> findById(LoanId id);

    List<Loan> findByLibraryId(LibraryId libraryId);

    List<Loan> findByLibraryIdAndClientId(UUID libraryId, UUID clientId);

    List<Loan> findOverdueByLibraryId(UUID libraryId);
}
