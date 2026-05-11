package com.restaurante.restaurante.service;

import com.restaurante.restaurante.model.*;
import com.restaurante.restaurante.repository.*;
import com.restaurante.restaurante.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(ClienteRepository clienteRepository,
                       AdminRepository adminRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.clienteRepository = clienteRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String senha) {
        var cliente = clienteRepository.findByEmail(email);
        if (cliente.isPresent() && passwordEncoder.matches(senha, cliente.get().getSenha())) {
            return jwtUtil.gerarToken(email, cliente.get().getRole().name());
        }
        var admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && passwordEncoder.matches(senha, admin.get().getSenha())) {
            return jwtUtil.gerarToken(email, RoleUsuario.ADMIN.name());
        }
        throw new RuntimeException("Credenciais inválidas");
    }

    public ClienteModel registrar(String nome, String email, String senha) {
        if (clienteRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        ClienteModel cliente = new ClienteModel();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(passwordEncoder.encode(senha));
        cliente.setRole(RoleUsuario.CLIENTE);
        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }
}