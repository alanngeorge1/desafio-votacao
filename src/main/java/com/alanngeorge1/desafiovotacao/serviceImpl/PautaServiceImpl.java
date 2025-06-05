package com.alanngeorge1.desafiovotacao.serviceImpl;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.alanngeorge1.desafiovotacao.dto.ResultadoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.exception.PautaNotFoundException;
import com.alanngeorge1.desafiovotacao.repository.PautaRepository;
import com.alanngeorge1.desafiovotacao.repository.VotoRepository;
import com.alanngeorge1.desafiovotacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class PautaServiceImpl implements PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Override
    public Pauta criarPauta(PautaDTO pautaDTO) {
        log.info("Iniciando criação de pauta com título='{}'", pautaDTO.getTitulo());

        Pauta pauta = Pauta.builder()
                .titulo(pautaDTO.getTitulo())
                .descricao(pautaDTO.getDescricao())
                .build();

        pauta = pautaRepository.save(pauta);

        log.info("Pauta criada com sucesso: ID={}, título='{}'", pauta.getId(), pauta.getTitulo());

        return pauta;
    }

    @Override
    public List<PautaDTO> listarPautas() {
        log.info("Listando todas as pautas");

        List<PautaDTO> pautas = pautaRepository.findAll().stream()
                .map(pauta -> {
                    PautaDTO dto = new PautaDTO();
                    dto.setTitulo(pauta.getTitulo());
                    dto.setDescricao(pauta.getDescricao());
                    return dto;
                })
                .toList();

        log.info("Total de pautas encontradas: {}", pautas.size());

        return pautas;
    }

    @Override
    public Pauta buscarPorId(Long id) {
        log.info("Buscando pauta por ID={}", id);

        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pauta com ID={} não encontrada", id);
                    return new PautaNotFoundException("Pauta com id " + id + " não encontrada");
                });

        log.info("Pauta encontrada: ID={}, título='{}'", pauta.getId(), pauta.getTitulo());

        return pauta;
    }

    @Override
    public ResultadoVotacaoDTO getResultadoVotacao(Long pautaId) {
        log.info("Calculando resultado da votação para pauta ID={}", pautaId);

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> {
                    log.warn("Pauta com ID={} não encontrada para cálculo de votação", pautaId);
                    return new PautaNotFoundException("Pauta com id " + pautaId + " não encontrada");
                });

        Long totalSim = votoRepository.countByPautaIdAndVoto(pautaId, "SIM");
        Long totalNao = votoRepository.countByPautaIdAndVoto(pautaId, "NAO");

        log.info("Votos contabilizados para pauta ID={}: SIM={}, NAO={}", pautaId, totalSim, totalNao);

        String resultado = (totalSim > totalNao) ? "APROVADA" : "REPROVADA";

        log.info("Resultado da votação para pauta ID={}: {}", pautaId, resultado);

        return new ResultadoVotacaoDTO(pautaId, totalSim, totalNao, resultado);
    }
}
