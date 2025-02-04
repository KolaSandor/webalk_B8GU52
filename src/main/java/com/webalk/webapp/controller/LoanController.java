package com.webalk.webapp.controller;

import com.webalk.webapp.entity.Loan;
import com.webalk.webapp.service.LoanService;
import com.webalk.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final UserService userService;


    @Autowired
    public LoanController(LoanService loanService, UserService userService) {
        this.loanService = loanService;
        this.userService = userService;
    }

    @GetMapping
    public String listLoans(Model model, Authentication auth) {

        List<Loan> loans = loanService.getLoansToUser(userService.getUserByUsername(auth.getName()).get().getId());
        model.addAttribute("loansToUser", loans);
        //model.addAttribute("loans", loans);
        return "loans";
    }

    @GetMapping("/add")
    public String addLoanForm(Model model) {
        model.addAttribute("loan", new Loan());
        return "add-loan";
    }

    @PostMapping("/add")
    public String addLoan(@RequestParam Long bookId, @RequestParam Long userId) {
        loanService.saveLoan(bookId, userId);
        return "redirect:/loans";
    }

    @GetMapping("/edit/{id}")
    public String editLoanForm(@PathVariable Long id, Model model) {
        Loan loan = loanService.getLoanById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + id));
        model.addAttribute("loan", loan);
        return "edit-loan";
    }

    @PostMapping("/edit/{id}")
    public String updateLoan(@PathVariable Long id, @ModelAttribute Loan loan) {
        loanService.updateLoan(id, loan);
        return "redirect:/loans";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "redirect:/loans";
    }
}
