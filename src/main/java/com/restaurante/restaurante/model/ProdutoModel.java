package com.restaurante.restaurante.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produto_model")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal preco;
    private String categoria;
    private String imgUrl;
    @Enumerated(EnumType.STRING)
    private UnidadeMedidaProduto vendaPor;
    private String descricao;
}
