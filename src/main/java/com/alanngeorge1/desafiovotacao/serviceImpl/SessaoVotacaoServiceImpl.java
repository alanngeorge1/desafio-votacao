package com.alanngeorge1.desafiovotacao.serviceImpl;

import com.alanngeorge1.desafiovotacao.dto.SessaoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.entity.SessaoVotacao;
import com.alanngeorge1.desafiovotacao.exception.PautaNotFoundException;
import com.alanngeorge1.desafiovotacao.repository.PautaRepository;
import com.alanngeorge1.desafiovotacao.repository.SessaoVotacaoRepository;
import com.alanngeorge1.desafiovotacao.service.SessaoVotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoVotacaoServiceImpl  implements SessaoVotacaoService {

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Override
    public SessaoVotacao abrirSessao(SessaoVotacaoDTO sessaoVotacaoDTO){

        Pauta pauta = pautaRepository.findById(sessaoVotacaoDTO.getPautaId())
                .orElseThrow(() -> new PautaNotFoundException("Pauta com id " + sessaoVotacaoDTO.getPautaId() + " Não encontrada. " ));

        int duracao = (sessaoVotacaoDTO.getDuracaoEmMinutos() == null || sessaoVotacaoDTO.getDuracaoEmMinutos() <= 0) ? 1 : sessaoVotacaoDTO.getDuracaoEmMinutos();

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = LocalDateTime.now().plusMinutes(duracao);

        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .pauta(pauta)
                .dataHoraInicio(inicio)
                .dataHoraFim(fim).
                build();
        return sessaoVotacaoRepository.save(sessaoVotacao);
    }
}
