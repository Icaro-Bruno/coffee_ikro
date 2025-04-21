package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ClienteModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClienteRequest {   //ENTRADA
    private String nome;
    private String telefone;
    private String endereco;

}
