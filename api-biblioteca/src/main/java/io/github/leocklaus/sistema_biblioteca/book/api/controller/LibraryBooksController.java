package io.github.leocklaus.sistema_biblioteca.book.api.controller;

import io.github.leocklaus.sistema_biblioteca.book.api.dto.BookResponse;
import io.github.leocklaus.sistema_biblioteca.book.application.usecase.GetBooksByLibraryUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/libraries/{libraryId}/books")
public class LibraryBooksController {

    private final GetBooksByLibraryUseCase getBooksByLibraryUseCase;

    public LibraryBooksController(GetBooksByLibraryUseCase getBooksByLibraryUseCase) {
        this.getBooksByLibraryUseCase = getBooksByLibraryUseCase;
    }

    @GetMapping
    public List<BookResponse> getBooks(@PathVariable UUID libraryId) {
        return getBooksByLibraryUseCase.getByLibrary(libraryId)
                .stream()
                .map(BookResponse::from)
                .toList();
    }
}
