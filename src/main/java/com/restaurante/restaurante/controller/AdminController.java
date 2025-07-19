package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.model.AdminModel;
import com.restaurante.restaurante.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminRepository repository;

    @PostMapping("/cadastraradmin")
    public AdminModel criarAdmin(@RequestBody AdminModel admin) {
        return repository.save(admin);
    }

    @GetMapping("/listar")
    public List<AdminModel> listar() {
        return repository.findAll();
    }
}
