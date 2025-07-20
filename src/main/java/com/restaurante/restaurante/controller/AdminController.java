package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.model.AdminModel;
import com.restaurante.restaurante.repository.AdminRepository;
import com.restaurante.restaurante.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminRepository repository;
    @Autowired
    private AdminService service;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/cadastraradmin")
    public ResponseEntity<AdminModel> criarAdmin(@RequestBody AdminModel admin) {
        return ResponseEntity.ok(service.salvar(admin));
    }

    @GetMapping("/listar")
    public List<AdminModel> listar() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        return repository.findById(id).map(admin -> {
            repository.delete(admin);
            return ResponseEntity.ok("Admin deletado");
        }).orElse(ResponseEntity.status(404).body("Admin não encontrado."));
    }
}
