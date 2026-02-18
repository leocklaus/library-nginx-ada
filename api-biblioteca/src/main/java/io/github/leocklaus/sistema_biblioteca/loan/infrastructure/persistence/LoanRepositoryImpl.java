package io.github.leocklaus.sistema_biblioteca.loan.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.Loan;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanId;
import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanStatus;
import io.github.leocklaus.sistema_biblioteca.loan.domain.repository.LoanRepository;
import io.github.leocklaus.sistema_biblioteca.loan.infrastructure.mapper.LoanMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanRepositoryImpl implements LoanRepository {

    private final JpaLoanRepository repository;

    public LoanRepositoryImpl(JpaLoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Loan loan) {
        repository.save(LoanMapper.toEntity(loan));
    }

    @Override
    public Optional<Loan> findById(LoanId id) {
        return repository.findById(id.value())
                .map(LoanMapper::toDomain);
    }

    @Override
    public List<Loan> findByLibraryId(LibraryId libraryId) {
        return repository.findByLibraryId(libraryId.value())
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findByLibraryIdAndClientId(UUID libraryId, UUID clientId) {
        return repository.findByLibraryIdAndClientId(libraryId, clientId)
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findOverdueByLibraryId(UUID libraryId) {
        return repository.findOverdueByLibraryId(
                        libraryId,
                        LocalDate.now(),
                        LoanStatus.ATRASADO
                )
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }
}
