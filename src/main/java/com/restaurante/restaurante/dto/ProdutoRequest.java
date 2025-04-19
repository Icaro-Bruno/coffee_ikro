package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.UnidadeMedidaProduto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoRequest {//entrada do adimn
    private String nome;
    private BigDecimal preco;
    private String categoria;
    private String imgUrl;
    private String descricao;
    private UnidadeMedidaProduto vendaPor;
}
