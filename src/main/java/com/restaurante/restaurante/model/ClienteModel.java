package com.restaurante.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente_model")
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String endereco;
}
