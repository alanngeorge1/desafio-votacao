package com.alanngeorge1.desafiovotacao.service;

import com.alanngeorge1.desafiovotacao.dto.VotoDTO;
import com.alanngeorge1.desafiovotacao.entity.Voto;
import org.springframework.stereotype.Service;

@Service
public interface VotoService {
    Voto registrarVoto(VotoDTO votoDto);
}
