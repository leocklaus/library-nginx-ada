package io.github.leocklaus.sistema_biblioteca.security.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("As credenciais são inválidas!");
    }
}
