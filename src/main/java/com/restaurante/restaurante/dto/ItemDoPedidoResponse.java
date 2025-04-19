package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ProdutoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ItemDoPedidoResponse {
    private Long id;
    private ProdutoResponse produto;
    private Integer quantidade;
    private BigDecimal subtotal;
    private BigDecimal precoUnitario;
}
