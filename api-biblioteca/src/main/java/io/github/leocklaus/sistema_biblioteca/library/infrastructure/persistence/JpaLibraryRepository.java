package io.github.leocklaus.sistema_biblioteca.library.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface JpaLibraryRepository extends JpaRepository<LibraryEntity, UUID>{
    boolean existsByEmail(String email);
}
