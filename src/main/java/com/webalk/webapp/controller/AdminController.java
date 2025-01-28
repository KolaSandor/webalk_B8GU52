package com.webalk.webapp.controller;

import com.webalk.webapp.entity.*;
import com.webalk.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
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
        existingUser.setPassword(user.getPassword());
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
}
