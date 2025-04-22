package com.restaurante.restaurante.dto;

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
    private PedidoResponse pedido;
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
    }


    public ItemDoPedidoResponse(Long id, ProdutoModel produto, PedidoModel pedido, Integer quantidade, BigDecimal subtotal, BigDecimal precoUnitario) {
        this.id = id;
        this.produto = new ProdutoResponse(produto); // Se tiver um DTO específico para Produto
        this.pedido = new PedidoResponse(pedido);   // Se houver um DTO para Pedido
        this.quantidade = quantidade;
        this.subtotal = subtotal;
        this.precoUnitario = precoUnitario;
    }
}
