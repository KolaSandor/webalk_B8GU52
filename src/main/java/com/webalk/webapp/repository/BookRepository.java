package com.webalk.webapp.repository;

import com.webalk.webapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author); // Keresés szerző alapján

    List<Book> findByCategoryId(Long categoryId); // Keresés kategória alapján
}