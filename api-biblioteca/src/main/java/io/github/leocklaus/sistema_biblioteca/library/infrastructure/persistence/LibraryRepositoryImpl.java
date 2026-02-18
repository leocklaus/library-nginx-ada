package io.github.leocklaus.sistema_biblioteca.library.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.library.domain.repository.LibraryRepository;
import io.github.leocklaus.sistema_biblioteca.library.infrastructure.mapper.LibraryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class LibraryRepositoryImpl implements LibraryRepository {

    private final JpaLibraryRepository repository;
    private final LibraryMapper mapper;

    public LibraryRepositoryImpl(JpaLibraryRepository repository, LibraryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Library save(Library library) {
        LibraryEntity entity = mapper.toEntity(library);
         repository.save(entity);
         return mapper.toDomain(entity);
    }

    @Override
    public Optional<Library> findById(LibraryId id) {
        return repository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public List<Library> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDomain).toList();
    }
}
