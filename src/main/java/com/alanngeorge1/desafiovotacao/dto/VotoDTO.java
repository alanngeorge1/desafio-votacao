package com.alanngeorge1.desafiovotacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VotoDTO {

    @NotNull(message = "pauta_id é Obrigatório")
    private Long pautaId;

    @NotBlank(message = "associdoId é Obrigatório")
    private String associadoId;

    @NotBlank(message = "voto é obrigatório (Sim ou Não)")
    private String voto;

}
