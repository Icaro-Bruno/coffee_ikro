package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.CanalPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Getter
public class PedidoRequest {
    private ClienteRequest cliente;
    private List<ItemDoPedidoRequest> itens;
    private String troco;
    private CanalPedido canalPedido;
    private String formaPagamento;
}