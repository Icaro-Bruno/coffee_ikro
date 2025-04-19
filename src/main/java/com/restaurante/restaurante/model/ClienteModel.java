package com.restaurante.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cliente_model")
@Data

public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String endereco;
}
