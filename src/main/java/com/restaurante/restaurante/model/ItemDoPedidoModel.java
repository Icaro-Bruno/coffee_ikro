package com.restaurante.restaurante.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itemdopedido_model")
public class ItemDoPedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoModel produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoModel pedido;

    private Integer quantidade;
    private BigDecimal precoUnitario; //valor do item no ato da compra
    private BigDecimal subtotal;

}
