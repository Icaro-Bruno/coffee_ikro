package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AtualizarPedidoRequest {
    private List<AtualizarItemDoPedRequest> itens;
    private StatusPedido novoStatus;
}
