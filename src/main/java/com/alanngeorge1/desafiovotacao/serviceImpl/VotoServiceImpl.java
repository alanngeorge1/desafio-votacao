package com.alanngeorge1.desafiovotacao.serviceImpl;

import com.alanngeorge1.desafiovotacao.dto.VotoDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.entity.Voto;
import com.alanngeorge1.desafiovotacao.exception.PautaNotFoundException;
import com.alanngeorge1.desafiovotacao.exception.RuntimeBusinessException;
import com.alanngeorge1.desafiovotacao.repository.PautaRepository;
import com.alanngeorge1.desafiovotacao.repository.VotoRepository;
import com.alanngeorge1.desafiovotacao.service.VotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VotoServiceImpl implements VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Override
    public Voto registrarVoto(VotoDTO votoDto) {
        log.info("Iniciando registro de voto: pautaId={}, associadoId={}, voto='{}'",
                votoDto.getPautaId(),
                votoDto.getAssociadoId(),
                votoDto.getVoto());

        Pauta pauta = pautaRepository.findById(votoDto.getPautaId())
                .orElseThrow(() -> {
                    log.warn("Pauta com ID={} não encontrada ao tentar registrar voto", votoDto.getPautaId());
                    return new PautaNotFoundException("Pauta com id " + votoDto.getPautaId() + " não encontrada.");
                });

        votoRepository.findByPautaIdAndAssociadoId(votoDto.getPautaId(), votoDto.getAssociadoId())
                .ifPresent(v -> {
                    log.warn("Associado ID={} já votou na pauta ID={}", votoDto.getAssociadoId(), votoDto.getPautaId());
                    throw new RuntimeBusinessException("Associado já votou nesta pauta.");
                });

        String votoFormatado = votoDto.getVoto().trim().toUpperCase();
        if (!votoFormatado.equals("SIM") && !votoFormatado.equals("NAO")) {
            log.warn("Voto inválido recebido: '{}'. Deve ser 'Sim' ou 'Não'.", votoDto.getVoto());
            throw new RuntimeBusinessException("Voto inválido. Deve ser 'Sim' ou 'Não'.");
        }

        Voto voto = Voto.builder()
                .pauta(pauta)
                .associadoId(votoDto.getAssociadoId())
                .voto(votoFormatado)
                .build();

        voto = votoRepository.save(voto);

        log.info("Voto registrado com sucesso: votoId={}, pautaId={}, associadoId={}",
                voto.getId(),
                voto.getPauta().getId(),
                voto.getAssociadoId());

        return voto;
    }
}
