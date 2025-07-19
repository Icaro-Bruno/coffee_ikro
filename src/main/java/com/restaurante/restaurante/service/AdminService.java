package com.restaurante.restaurante.service;

import com.restaurante.restaurante.model.AdminModel;
import com.restaurante.restaurante.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean autenticar(String usuario, String senha) {
        Optional<AdminModel> buscaAdmin = adminRepository.findByUsuario(usuario);
        return buscaAdmin.isPresent() && buscaAdmin.get().getSenha().equals(senha);
    }
}
