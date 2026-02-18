package io.github.leocklaus.sistema_biblioteca.loan.domain.model;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.ClientId;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

import java.time.LocalDate;

public class Loan {

    private final LoanId id;
    private final LibraryId libraryId;
    private final ClientId clientId;
    private final BookId bookId;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;

    private Loan(
            LoanId id,
            LibraryId libraryId,
            ClientId clientId,
            BookId bookId,
            LocalDate loanDate,
            LocalDate dueDate,
            LocalDate returnDate,
            LoanStatus status
    ) {
        this.id = id;
        this.libraryId = libraryId;
        this.clientId = clientId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public static Loan create(
            LibraryId libraryId,
            ClientId clientId,
            BookId bookId,
            LocalDate loanDate,
            LocalDate dueDate
    ) {
        if (loanDate == null || dueDate == null) {
            throw new DomainException("Datas de empréstimo e devolução são obrigatórias");
        }
        return new Loan(
                LoanId.newId(),
                libraryId,
                clientId,
                bookId,
                loanDate,
                dueDate,
                null,
                LoanStatus.EMPRESTADO
        );
    }

    public void markReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.status = returnDate.isAfter(dueDate) ? LoanStatus.ATRASADO : LoanStatus.DEVOLVIDO;
    }

    public LoanId getId() {
        return id;
    }

    public LibraryId getLibraryId() {
        return libraryId;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public BookId getBookId() {
        return bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public static Loan rehydrate(
            LoanId id,
            LibraryId libraryId,
            ClientId clientId,
            BookId bookId,
            LocalDate loanDate,
            LocalDate dueDate,
            LocalDate returnDate,
            LoanStatus status
    ) {
        return new Loan(id, libraryId, clientId, bookId, loanDate, dueDate, returnDate, status);
    }
}
