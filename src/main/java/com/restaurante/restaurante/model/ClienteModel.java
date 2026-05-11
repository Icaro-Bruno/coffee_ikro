package com.restaurante.restaurante.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente_model")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String endereco;

    private String senha;

    @Enumerated(EnumType.STRING)
    private RoleUsuario role = RoleUsuario.CLIENTE;
    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(nullable = true, unique = true)
    private String email;
}
