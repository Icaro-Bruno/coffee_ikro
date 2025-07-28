package com.restaurante.restaurante.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteForm {
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
}
