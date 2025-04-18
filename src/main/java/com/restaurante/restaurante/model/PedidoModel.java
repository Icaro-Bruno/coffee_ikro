package com.restaurante.restaurante.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido_model")
public class PedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<ItemDoPedidoModel> itens;
    private BigDecimal total;
    private ClienteModel cliente;
    private StatusPedido status;
    private LocalDateTime dataHora;


}
