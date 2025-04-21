package com.restaurante.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PromocaoResponse { //SAIDA
    private Long id;
    private String titulo;
    private String imgUrl;
    private boolean ativo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}
