package io.github.leocklaus.sistema_biblioteca.user.infrastructure.mapper;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.Role;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.UserId;
import io.github.leocklaus.sistema_biblioteca.user.infrastructure.persistence.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserEntity toEntity(User user) {

        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::asString)
                .collect(Collectors.toSet());

        return new UserEntity(
                user.getId().value(),
                user.getLibraryId().value(),
                user.getEmail(),
                user.getPasswordHash(),
                roles,
                user.getStatus(),
                user.getCreatedAt()
        );
    }

    public User toDomain(UserEntity entity) {

        Set<Role> roles = entity.getRoles()
                .stream()
                .map(Role::fromString)
                .collect(Collectors.toSet());

        return User.rehydrate(
                new UserId(entity.getId()),
                new LibraryId(entity.getLibraryId()),
                entity.getEmail(),
                entity.getPasswordHash(),
                roles,
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
