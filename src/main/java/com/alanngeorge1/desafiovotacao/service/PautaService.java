package com.alanngeorge1.desafiovotacao.service;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;

import java.util.List;

public interface PautaService {

    Pauta criarPauta(PautaDTO pautaDTO);

    List<PautaDTO> listarPautas();

    Pauta buscarPorId(Long id);
}
