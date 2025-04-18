package com.restaurante.restaurante.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "promocao_model")
public class PromocaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String imgUrl;
    private boolean ativo;

    private LocalDate dataInicio;
    private LocalDate dataFim;

}
