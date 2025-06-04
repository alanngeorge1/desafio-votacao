package com.alanngeorge1.desafiovotacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoVotacaoDTO {
    private Long pautaId;
    private Long totalSim;
    private Long totalNao;
    private String resultado;
}
