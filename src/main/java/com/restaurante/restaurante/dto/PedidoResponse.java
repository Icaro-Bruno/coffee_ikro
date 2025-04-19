package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoResponse { //SAIDA
    private Long id;
    private List<ItemDoPedidoResponse> itens;
    private BigDecimal total;
    private ClienteResponse cliente;
    private StatusPedido status;
    private LocalDateTime dataHora;
}
