package com.alanngeorge1.desafiovotacao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PautaDTO {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String descricao;
}
