package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ClienteRequest {   //ENTRADA
    private String nome;
    private String telefone;
    private String endereco;
}
