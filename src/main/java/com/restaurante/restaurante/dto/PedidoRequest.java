package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PedidoRequest { //ENTRADA
    private List<ItemDoPedidoRequest> itens; //talvez um itemId;
    private Long clienteId;
}
