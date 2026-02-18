package io.github.leocklaus.sistema_biblioteca.library.application.usecase;

import io.github.leocklaus.sistema_biblioteca.library.application.command.RegisterLibraryCommand;
import io.github.leocklaus.sistema_biblioteca.library.application.result.RegisterLibraryResult;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;
import io.github.leocklaus.sistema_biblioteca.library.domain.repository.LibraryRepository;
import io.github.leocklaus.sistema_biblioteca.security.jwt.TokenService;
import io.github.leocklaus.sistema_biblioteca.user.application.command.CreateAdminUserCommand;
import io.github.leocklaus.sistema_biblioteca.user.application.usecase.CreateAdminUseCase;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegisterLibraryUseCase {
    private final LibraryRepository libraryRepository;
    private final CreateAdminUseCase createAdminUseCase;
    private final TokenService tokenService;

    public RegisterLibraryUseCase(
            LibraryRepository libraryRepository,
            CreateAdminUseCase createAdminUseCase, TokenService tokenService
    ) {
        this.libraryRepository = libraryRepository;
        this.createAdminUseCase = createAdminUseCase;
        this.tokenService = tokenService;
    }

    public RegisterLibraryResult execute(RegisterLibraryCommand command) {

        Library library = Library.create(
                command.libraryName(),
                command.libraryEmail()
        );

        libraryRepository.save(library);

        User admin = createAdminUseCase.execute(new CreateAdminUserCommand(
                library.getId(),
                command.adminEmail(),
                command.adminPassword()
        ));

        String token = tokenService.generateToken(admin);

        return new RegisterLibraryResult(
                library.getId().value(),
                admin.getId().value(),
                token
        );
    }
}
