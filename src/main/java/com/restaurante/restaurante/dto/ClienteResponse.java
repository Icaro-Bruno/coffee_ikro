package com.restaurante.restaurante.dto;

import com.restaurante.restaurante.model.ClienteModel;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse { //saida
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;

    @Column(nullable = false)
    private Boolean ativo = true;

    public ClienteResponse(ClienteModel cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
        this.endereco = cliente.getEndereco();
        this.ativo = cliente.getAtivo();
    }

}
