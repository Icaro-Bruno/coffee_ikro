package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.model.ClienteModel;
import com.restaurante.restaurante.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String token = authService.login(body.get("email"), body.get("senha"));
            return ResponseEntity.ok(Map.of(
                    "accessToken", token,
                    "tokenType", "Bearer",
                    "expiresIn", 86400
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "CREDENCIAIS_INVALIDAS",
                    "message", e.getMessage(),
                    "path", "/api/auth/login"
            ));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Map<String, String> body) {
        try {
            ClienteModel cliente = authService.registrar(
                    body.get("nome"), body.get("email"), body.get("senha")
            );
            return ResponseEntity.status(201).body(Map.of(
                    "id", cliente.getId(),
                    "nome", cliente.getNome(),
                    "email", cliente.getEmail(),
                    "role", cliente.getRole()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(Map.of(
                    "error", "EMAIL_JA_CADASTRADO",
                    "message", e.getMessage(),
                    "path", "/api/auth/registrar"
            ));
        }
    }
}