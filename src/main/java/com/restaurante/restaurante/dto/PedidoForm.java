package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PedidoForm {
    private String nome;
    private String telefone;
    private String endereco;
    private String troco;
}
