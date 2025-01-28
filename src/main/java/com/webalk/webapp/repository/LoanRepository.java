package com.webalk.webapp.repository;

import com.webalk.webapp.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId); // Egy felhasználó kölcsönzései

    List<Loan> findByBookId(Long bookId); // Egy konkrét könyv kölcsönzései
}
