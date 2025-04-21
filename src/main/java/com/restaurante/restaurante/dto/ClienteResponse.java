package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ClienteModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ClienteResponse { //saida
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;

    public ClienteResponse(ClienteModel cliente) {
    }
}
