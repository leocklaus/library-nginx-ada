package io.github.leocklaus.sistema_biblioteca.loan.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.loan.domain.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface JpaLoanRepository extends JpaRepository<LoanEntity, UUID> {
    List<LoanEntity> findByLibraryId(UUID libraryId);
    List<LoanEntity> findByLibraryIdAndClientId(
            UUID libraryId,
            UUID clientId
    );

    @Query("""
        select l from LoanEntity l
        where l.libraryId = :libraryId
          and l.dueDate < :today
          and l.status = :status
    """)
    List<LoanEntity> findOverdueByLibraryId(
            @Param("libraryId") UUID libraryId,
            @Param("today") LocalDate today,
            @Param("status") LoanStatus status
    );
}
