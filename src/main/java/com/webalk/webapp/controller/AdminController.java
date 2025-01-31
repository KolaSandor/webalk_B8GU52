package com.webalk.webapp.controller;

import com.webalk.webapp.entity.*;
import com.webalk.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, BookService bookService, CategoryService categoryService) {
        this.userService = userService;
        this.roleService = roleService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin-books")
    public String adminBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin-books";
    }

    @GetMapping("/add-user")
    public String addUserForm(Model model) {
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty()) {
            throw new IllegalStateException("No roles available in the database.");
        }
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user, @RequestParam("roleId") Long roleId) {
        Role role = roleService.getRoleById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));
        user.setRoles(Set.of(role)); // Javított típuskezelés
        userService.saveUser(user);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit-user/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty()) {
            throw new IllegalStateException("No roles available for editing.");
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "edit-user";
    }

    @PostMapping("/edit-user/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam("roleId") Long roleId) {
        User existingUser = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        Role role = roleService.getRoleById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));
        existingUser.setUsername(user.getUsername());
        if(user.getPassword() != null)
            existingUser.setPassword(user.getPassword());
        else
            existingUser.setPassword(existingUser.getPassword());
        existingUser.setRoles(Set.of(role)); // Javított típuskezelés
        userService.saveUser(existingUser);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete user. User with ID: " + id + " does not exist.");
        }
        userService.deleteUser(id); // Javított metódushivatkozás
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isEmpty()) {
            throw new IllegalArgumentException("A könyv nem található az azonosító alapján: " + id);
        }
        model.addAttribute("book", book.get());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "edit-book";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/admin/admin-books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin/admin-books";
    }

}
