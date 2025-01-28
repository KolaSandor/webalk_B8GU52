package com.webalk.webapp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Boolean borrowed = false;

    @ManyToOne
    @JoinColumn(name = "borrowed_by")
    private User borrowedBy;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(User borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
