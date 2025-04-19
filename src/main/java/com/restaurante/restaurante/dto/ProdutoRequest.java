package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoRequest {//entrada
    private String nome;
    private BigDecimal preco;
    private String categoria;
    private String imgUrl;
}
