package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PedidoRequest { //ENTRADA
    private ClienteRequest cliente;
    private List<ItemDoPedidoRequest> itens;
    private String troco;
}
