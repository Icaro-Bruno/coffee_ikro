package com.restaurante.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//enviar pra o front
public class ProdutoResponse {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String imgUrl;
    private String categoria;
}
