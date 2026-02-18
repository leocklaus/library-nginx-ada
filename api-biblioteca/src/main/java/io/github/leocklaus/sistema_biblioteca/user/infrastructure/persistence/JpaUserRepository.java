package io.github.leocklaus.sistema_biblioteca.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
    List<UserEntity> findAllByLibraryId(UUID libraryId);
    Optional<UserEntity> findByEmail(String email);
}
