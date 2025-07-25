package com.restaurante.restaurante.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Getter
@Setter
public class PromocaoForm {
    private String titulo;
    private String descricao;
    private MultipartFile imagem;
    private boolean ativo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String imgUrl;
    
}
