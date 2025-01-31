package com.webalk.webapp.service;

import com.webalk.webapp.entity.Book;
import com.webalk.webapp.entity.Loan;
import com.webalk.webapp.entity.User;
import com.webalk.webapp.repository.BookRepository;
import com.webalk.webapp.repository.LoanRepository;
import com.webalk.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansToUser(long id) {
        return loanRepository.findByUserId(id);
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan saveLoan(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (book.getBorrowed()) {
            throw new RuntimeException("The book is already borrowed");
        }

        book.setBorrowed(true);
        book.setBorrowedBy(user);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(new Date());
        return loanRepository.save(loan);
    }

    public void updateLoan(Long id, Loan updatedLoan) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + id));

        existingLoan.setLoanDate(updatedLoan.getLoanDate());
        existingLoan.setReturnDate(updatedLoan.getReturnDate());
        loanRepository.save(existingLoan);
    }

    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));
        Book book = loan.getBook();

        book.setBorrowed(false);
        book.setBorrowedBy(null);
        bookRepository.save(book);

        loan.setReturnDate(new Date());
        loanRepository.save(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
