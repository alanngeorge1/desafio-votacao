package com.alanngeorge1.desafiovotacao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SessaoVotacaoDTO {

    @NotNull(message = "pauta_id é obrigatório")
    private long pautaId;

    private Integer duracaoEmMinutos;

}
