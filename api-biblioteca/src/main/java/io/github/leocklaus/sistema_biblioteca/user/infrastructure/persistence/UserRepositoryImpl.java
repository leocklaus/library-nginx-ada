package io.github.leocklaus.sistema_biblioteca.user.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.UserId;
import io.github.leocklaus.sistema_biblioteca.user.domain.repository.UserRepository;
import io.github.leocklaus.sistema_biblioteca.user.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository repository;
    private final UserMapper mapper;

    public UserRepositoryImpl(JpaUserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        var entity = mapper.toEntity(user);
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public List<User> findAllByLibraryId(LibraryId libraryId) {
        return repository.findAllByLibraryId(libraryId.value())
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findLibrariansByLibraryId(UUID libraryId) {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .filter(user -> user.isLibrarianOf(new LibraryId(libraryId)))
                .toList();
    }
}
