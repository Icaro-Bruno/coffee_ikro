package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ProdutoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ItemDoPedidoRequest {//ENTRADA

    private Long produtoId;
    private Integer quantidade;
}
