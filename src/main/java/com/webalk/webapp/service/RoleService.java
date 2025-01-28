package com.webalk.webapp.service;

import com.webalk.webapp.entity.*;
import com.webalk.webapp.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Összes szerep lekérdezése.
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Szerep lekérése azonosító alapján.
     */
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    /**
     * Új szerep mentése vagy meglévő frissítése.
     */
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Szerep törlése azonosító alapján.
     */
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("A szerep azonosítóval " + id + " nem létezik.");
        }
        roleRepository.deleteById(id);
    }
}
