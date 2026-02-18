package io.github.leocklaus.sistema_biblioteca.library.application.usecase;

import io.github.leocklaus.sistema_biblioteca.library.domain.exception.LibraryNotFoundException;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.library.domain.repository.LibraryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetLibraryUseCase {
    private final LibraryRepository libraryRepository;

    public GetLibraryUseCase(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Library getById(UUID id) {
        return libraryRepository.findById(new LibraryId(id))
                .orElseThrow(() -> new LibraryNotFoundException(id));
    }

    public List<Library> getAll() {
        return libraryRepository.findAll();
    }
}
