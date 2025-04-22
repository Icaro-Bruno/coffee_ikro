package com.restaurante.restaurante.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido_model")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ItemDoPedidoModel> itens;
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    private LocalDateTime dataHora;


}
