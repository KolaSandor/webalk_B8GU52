package com.webalk.webapp.service;

import com.webalk.webapp.entity.*;
import com.webalk.webapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setBorrowed(updatedBook.getBorrowed());
        existingBook.setBorrowedBy(updatedBook.getBorrowedBy());
        existingBook.setCategory(updatedBook.getCategory());
        bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
