package io.github.leocklaus.sistema_biblioteca.user.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.user.domain.model.Role;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID libraryId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false)
    private Instant createdAt;
}
