package com.restaurante.restaurante.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "promocao_model")
@Getter
@Setter
@NoArgsConstructor
public class PromocaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String imgUrl;
    private boolean ativo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}
