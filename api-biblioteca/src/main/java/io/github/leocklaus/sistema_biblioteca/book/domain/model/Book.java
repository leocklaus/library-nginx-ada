package io.github.leocklaus.sistema_biblioteca.book.domain.model;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

public class Book {

    private final BookId id;
    private final LibraryId libraryId;
    private final ISBN isbn;
    private final String title;
    private final String author;

    private Book(
            BookId id,
            LibraryId libraryId,
            ISBN isbn,
            String title,
            String author
    ) {
        this.id = id;
        this.libraryId = libraryId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public static Book create(
            LibraryId libraryId,
            ISBN isbn,
            String title,
            String author
    ) {
        return new Book(
                BookId.newId(),
                libraryId,
                isbn,
                title,
                author
        );
    }

    public static Book rehydrate(
            BookId id,
            LibraryId libraryId,
            ISBN isbn,
            String title,
            String author
    ) {
        return new Book(
                id,
                libraryId,
                isbn,
                title,
                author
        );
    }

    public BookId getId() { return id; }
    public LibraryId getLibraryId() { return libraryId; }
    public ISBN getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
}
