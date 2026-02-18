package io.github.leocklaus.sistema_biblioteca.library.infrastructure.mapper;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.library.infrastructure.persistence.LibraryEntity;
import org.springframework.stereotype.Component;

@Component
public class LibraryMapper {
    public LibraryEntity toEntity(Library library) {
        return new LibraryEntity(
                library.getId().value(),
                library.getName(),
                library.getEmail(),
                library.getStatus(),
                library.getCreatedAt()
        );
    }

    public Library toDomain(LibraryEntity entity) {
        return Library.rehydrate(
                new LibraryId(entity.getId()),
                entity.getName(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
