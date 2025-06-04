package com.alanngeorge1.desafiovotacao.serviceImpl;

import com.alanngeorge1.desafiovotacao.dto.VotoDto;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.entity.Voto;
import com.alanngeorge1.desafiovotacao.exception.PautaNotFoundException;
import com.alanngeorge1.desafiovotacao.exception.RuntimeBusinessException;
import com.alanngeorge1.desafiovotacao.repository.PautaRepository;
import com.alanngeorge1.desafiovotacao.repository.VotoRepository;
import com.alanngeorge1.desafiovotacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoServiceImpl implements VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    PautaRepository pautaRepository;

    @Override
    public Voto registrarVoto(VotoDto votoDto) {
        Pauta pauta = pautaRepository.findById(votoDto.getPautaId())
                .orElseThrow(() -> new PautaNotFoundException("Pauta com id " + votoDto.getPautaId() + " não encontrada." ));

        votoRepository.findByPautaIdAndAssociadoId(votoDto.getPautaId(), votoDto.getAssociadoId())
                .ifPresent(v -> {
                    throw new RuntimeBusinessException("Associado já votou nesta pauta.");
                });

        String votoFormatado = votoDto.getVoto().trim().toUpperCase();
        if (!votoFormatado.equals("SIM") && ! votoFormatado.equals("NAO")){
            throw new RuntimeBusinessException("Voto inválido. Deve ser 'Sim' ou 'Não'.");
        }

        Voto voto = Voto.builder()
                .pauta(pauta)
                .associadoId(votoDto.getAssociadoId())
                .voto(votoFormatado)
                .build();

        return votoRepository.save(voto);

    }

}
