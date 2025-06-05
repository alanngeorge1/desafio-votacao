package com.alanngeorge1.desafiovotacao.serviceImpl;

import com.alanngeorge1.desafiovotacao.dto.SessaoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.entity.SessaoVotacao;
import com.alanngeorge1.desafiovotacao.exception.PautaNotFoundException;
import com.alanngeorge1.desafiovotacao.repository.PautaRepository;
import com.alanngeorge1.desafiovotacao.repository.SessaoVotacaoRepository;
import com.alanngeorge1.desafiovotacao.service.SessaoVotacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class SessaoVotacaoServiceImpl implements SessaoVotacaoService {

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Override
    public SessaoVotacao abrirSessao(SessaoVotacaoDTO sessaoVotacaoDTO) {
        log.info("Iniciando abertura de sessão para pauta ID={} com duração={} minutos",
                sessaoVotacaoDTO.getPautaId(),
                sessaoVotacaoDTO.getDuracaoEmMinutos());

        Pauta pauta = pautaRepository.findById(sessaoVotacaoDTO.getPautaId())
                .orElseThrow(() -> {
                    log.warn("Pauta com ID={} não encontrada ao tentar abrir sessão", sessaoVotacaoDTO.getPautaId());
                    return new PautaNotFoundException("Pauta com id " + sessaoVotacaoDTO.getPautaId() + " não encontrada.");
                });

        int duracao = (sessaoVotacaoDTO.getDuracaoEmMinutos() == null || sessaoVotacaoDTO.getDuracaoEmMinutos() <= 0) ? 1 : sessaoVotacaoDTO.getDuracaoEmMinutos();

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusMinutes(duracao);

        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .pauta(pauta)
                .dataHoraInicio(inicio)
                .dataHoraFim(fim)
                .build();

        sessaoVotacao = sessaoVotacaoRepository.save(sessaoVotacao);

        log.info("Sessão de votação criada com sucesso: SessaoID={}, PautaID={}, Início={}, Fim={}",
                sessaoVotacao.getId(),
                pauta.getId(),
                inicio,
                fim);

        return sessaoVotacao;
    }
}
