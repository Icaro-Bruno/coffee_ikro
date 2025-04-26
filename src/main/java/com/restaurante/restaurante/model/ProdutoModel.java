package com.restaurante.restaurante.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import java.math.BigDecimal;

@Entity
@Table(name = "produto_model")
@Getter
@Setter
@NoArgsConstructor
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @NumberFormat(style = NumberFormat.Style.NUMBER,pattern = "#,##0.00")
    private BigDecimal preco;
    private String categoria;
    private String imgUrl;
    @Enumerated(EnumType.STRING)
    private UnidadeMedidaProduto vendaPor;
    private String descricao;

    public ProdutoModel(Long id, String descricao, UnidadeMedidaProduto vendaPor, String imgUrl, BigDecimal preco, String categoria, String nome) {
        this.id = id;
        this.descricao = descricao;
        this.vendaPor = vendaPor;
        this.imgUrl = imgUrl;
        this.preco = preco;
        this.categoria = categoria;
        this.nome = nome;
    }

    public ProdutoModel(Long produtoId) {
        this.id = produtoId;
    }

}
