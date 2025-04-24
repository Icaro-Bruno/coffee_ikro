package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PedidoRequest { //ENTRADA
    private Long clienteId;
    private List<ItemDoPedidoRequest> itens; //talvez um itemId;

}
