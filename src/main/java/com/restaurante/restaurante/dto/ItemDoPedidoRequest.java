package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ProdutoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemDoPedidoRequest {//ENTRADA
    private Long produtoId;
    private Integer quantidade;
}
