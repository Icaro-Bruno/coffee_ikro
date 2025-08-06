package com.restaurante.restaurante.service;

import com.restaurante.restaurante.model.AdminModel;
import com.restaurante.restaurante.model.RecuperacaoToken;
import com.restaurante.restaurante.repository.AdminRepository;
import com.restaurante.restaurante.repository.RecuperacaoTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RecuperacaoTokenRepository tokenRepository;
    @Autowired
    private JavaMailSender mailSender;

    public boolean autenticar(String usuario, String senha) {
        Optional<AdminModel> buscaAdmin = adminRepository.findByUsuario(usuario);
        return buscaAdmin.isPresent() && passwordEncoder.matches(senha, buscaAdmin.get().getSenha());
    }

    public AdminModel salvar(AdminModel admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        if (!admin.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("Formato de email inválido.");
        }
        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        return adminRepository.save(admin);
    }

    public boolean enviarLinkRecuperacao(String email){
        Optional<AdminModel> adminOpt = adminRepository.findByEmail(email);
        if(adminOpt.isEmpty()){
            return false;
        }
        String token = UUID.randomUUID().toString();
        RecuperacaoToken recuperacao = new RecuperacaoToken();
        recuperacao.setToken(token);
        recuperacao.setEmail(email);
        recuperacao.setExpiracao(LocalDateTime.now().plusHours(1));
        tokenRepository.save(recuperacao);

        // Monta o link
        String link = "http://localhost:8080/admin/redefinir?token=" + token;

        // Envia o e-mail
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(email);
        mensagem.setSubject("Recuperação de Senha");
        mensagem.setText("Olá! Clique no link abaixo para redefinir sua senha:\n" + link);
        mensagem.setFrom("ikrobr.dev@gmail.com"); // ou outro remetente válido

        mailSender.send(mensagem); // <-- aqui envia de verdade

        return true;
    }

    public boolean redefinirSenha(String token,String novaSenha){
        Optional<RecuperacaoToken> tokenOpt = tokenRepository.findByToken(token);
        if(tokenOpt.isEmpty()){
            return false;
        }
        RecuperacaoToken recuperacao = tokenOpt.get();
        if(recuperacao.getExpiracao().isBefore(LocalDateTime.now())){
            return false;
        }
        Optional<AdminModel> adminOpt = adminRepository.findByEmail(recuperacao.getEmail());
        if(adminOpt.isEmpty()){
            return false;
        }
        AdminModel admin = adminOpt.get();
        String senhaCriptografada = new BCryptPasswordEncoder().encode(novaSenha);
        admin.setSenha(senhaCriptografada);
        adminRepository.save(admin);
        tokenRepository.delete(recuperacao);
        return true;
    }

    public boolean tokenValido(String token) {
        Optional<RecuperacaoToken> tokenOpt = tokenRepository.findByToken(token);
        return tokenOpt.isPresent() && tokenOpt.get().getExpiracao().isAfter(LocalDateTime.now());
    }

}
