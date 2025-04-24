package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ClienteModel;
import com.restaurante.restaurante.model.ItemDoPedidoModel;
import com.restaurante.restaurante.model.PedidoModel;
import com.restaurante.restaurante.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public PedidoResponse(PedidoModel pedido) {
        this.id = pedido.getId();
        this.itens = pedido.getItens().stream()
                .map(ItemDoPedidoResponse::new).collect(Collectors.toList());
        this.total = pedido.getTotal();
        this.cliente = new ClienteResponse(pedido.getCliente());
        this.status = pedido.getStatus();
        this.dataHora = pedido.getDataHora();
    }

    public PedidoResponse(Long id, List<ItemDoPedidoResponse> itens, BigDecimal total, ClienteModel cliente, StatusPedido status, LocalDateTime dataHora) {
        this.id = id;
        this.itens = itens;
        this.total = total;
        this.cliente = new ClienteResponse(cliente);
        this.status = status;
        this.dataHora = dataHora;
    }
}
