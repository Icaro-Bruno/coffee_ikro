package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AtualizarItemDoPedRequest {
    private Long itemId;
    private Integer novaQuant;

}
