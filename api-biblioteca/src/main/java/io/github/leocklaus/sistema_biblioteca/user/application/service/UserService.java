package io.github.leocklaus.sistema_biblioteca.user.application.service;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.user.domain.exception.UserNotFoundException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.UserId;
import io.github.leocklaus.sistema_biblioteca.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(UUID id) {
        return userRepository.findById(new UserId(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getByLibrary(UUID libraryId) {
        return userRepository.findAllByLibraryId(new LibraryId(libraryId));
    }
}
