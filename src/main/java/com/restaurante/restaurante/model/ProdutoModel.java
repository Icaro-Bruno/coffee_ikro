package com.restaurante.restaurante.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto_model")
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal preco;

    private String categoria;

    private String imgUrl;


}
