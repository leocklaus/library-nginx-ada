package io.github.leocklaus.sistema_biblioteca.book.domain.exception;

public class InvalidISBNException extends RuntimeException {
    public InvalidISBNException(String message) {
        super("ISBN inválido: " + message);
    }
}
