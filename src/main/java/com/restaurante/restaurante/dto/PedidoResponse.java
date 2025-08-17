package com.restaurante.restaurante.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante.restaurante.model.ClienteModel;
import com.restaurante.restaurante.model.ItemDoPedidoModel;
import com.restaurante.restaurante.model.PedidoModel;
import com.restaurante.restaurante.model.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PedidoResponse { //SAIDA
    private Long id;
    private List<ItemDoPedidoResponse> itens;
    private BigDecimal total;
    @JsonIgnore
    private ClienteResponse cliente;
    private StatusPedido status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHora;
    private String dataHoraFormatada;

    private String nomeEntrega;
    private String telefoneEntrega;
    private String enderecoEntrega;

    public PedidoResponse(PedidoModel pedido) {
        this.id = pedido.getId();
        this.itens = pedido.getItens().stream()
                .map(ItemDoPedidoResponse::new).collect(Collectors.toList());
        this.total = pedido.getTotal();
        this.cliente = new ClienteResponse(pedido.getCliente());
        this.status = pedido.getStatus();
        this.dataHora = pedido.getDataHora();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        this.dataHoraFormatada = this.dataHora.format(formatter);

        this.nomeEntrega = pedido.getNomeEntrega();
        this.telefoneEntrega = pedido.getTelefoneEntrega();
        this.enderecoEntrega = pedido.getEnderecoEntrega();
    }

    public PedidoResponse(Long id, List<ItemDoPedidoResponse> itens, BigDecimal total, ClienteModel cliente, StatusPedido status, LocalDateTime dataHora) {
        this.id = id;
        this.itens = itens;
        this.total = total;
        this.cliente = new ClienteResponse(cliente);
        this.status = status;
        this.dataHora = dataHora;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        this.dataHoraFormatada = this.dataHora.format(formatter);
    }

}
