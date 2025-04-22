package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ProdutoModel;
import com.restaurante.restaurante.model.UnidadeMedidaProduto;
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
    private String descricao;
    private UnidadeMedidaProduto vendaPor;

    public ProdutoResponse(ProdutoModel produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
    }
}
