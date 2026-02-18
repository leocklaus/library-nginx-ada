package io.github.leocklaus.sistema_biblioteca.library.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "libraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private LibraryStatus status;

    @Column(nullable = false)
    private Instant createdAt;
}
