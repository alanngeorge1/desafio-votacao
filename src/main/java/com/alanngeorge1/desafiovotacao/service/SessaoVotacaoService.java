package com.alanngeorge1.desafiovotacao.service;

import com.alanngeorge1.desafiovotacao.dto.SessaoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.entity.SessaoVotacao;
import org.springframework.stereotype.Service;

@Service
public interface SessaoVotacaoService {
    SessaoVotacao abrirSessao(SessaoVotacaoDTO dto);
}
