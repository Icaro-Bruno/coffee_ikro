package com.restaurante.restaurante.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoForm {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
}
