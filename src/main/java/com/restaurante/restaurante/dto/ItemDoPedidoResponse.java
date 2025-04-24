package com.restaurante.restaurante.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante.restaurante.model.ItemDoPedidoModel;
import com.restaurante.restaurante.model.PedidoModel;
import com.restaurante.restaurante.model.ProdutoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Setter

public class ItemDoPedidoResponse {
    //private PedidoResponse pedido;
    private Long id;
    private ProdutoResponse produto;
    private Integer quantidade;
    private BigDecimal subtotal;
    private BigDecimal precoUnitario;

    public ItemDoPedidoResponse(ItemDoPedidoModel itemDoPedido) {
        this.id = itemDoPedido.getId();
        this.produto = new ProdutoResponse(itemDoPedido.getProduto());
        this.quantidade = itemDoPedido.getQuantidade();
        this.subtotal = itemDoPedido.getSubtotal();
        this.precoUnitario = itemDoPedido.getPrecoUnitario();
        //this.pedido = new PedidoResponse(itemDoPedido.getPedido());
    }

    public ItemDoPedidoResponse(Long id, ProdutoResponse produtoResponse, Integer quantidade, BigDecimal subtotal, BigDecimal precoUnitario) {
        this.id = id;
        this.produto = produtoResponse;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
        this.precoUnitario = precoUnitario;
    }

    public ItemDoPedidoResponse(Long id, ProdutoModel produto, Integer quantidade, BigDecimal subtotal, BigDecimal precoUnitario) {
        this.id = id;
        this.produto = new ProdutoResponse(produto); // conversão do model para DTO
        this.quantidade = quantidade;
        this.subtotal = subtotal;
        this.precoUnitario = precoUnitario;
    }
}
