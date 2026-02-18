package io.github.leocklaus.sistema_biblioteca.book.domain.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(){
        super("Livro não encontrado");
    }
}
