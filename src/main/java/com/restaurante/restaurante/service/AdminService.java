package com.restaurante.restaurante.service;

import com.restaurante.restaurante.model.AdminModel;
import com.restaurante.restaurante.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean autenticar(String usuario, String senha) {
        Optional<AdminModel> buscaAdmin = adminRepository.findByUsuario(usuario);
        return buscaAdmin.isPresent() && passwordEncoder.matches(senha, buscaAdmin.get().getSenha());
    }

    public AdminModel salvar(AdminModel admin) {
        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        return adminRepository.save(admin);
    }
}
