package com.alanngeorge1.desafiovotacao.serviceImpl;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.alanngeorge1.desafiovotacao.dto.ResultadoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.exception.PautaNotFoundException;
import com.alanngeorge1.desafiovotacao.repository.PautaRepository;
import com.alanngeorge1.desafiovotacao.repository.VotoRepository;
import com.alanngeorge1.desafiovotacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaServiceImpl implements PautaService {

    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private VotoRepository votoRepository;

    @Override
    public Pauta criarPauta(PautaDTO pautaDTO) {

        Pauta pauta = Pauta.builder()
                .titulo(pautaDTO.getTitulo())
                .descricao(pautaDTO.getDescricao())
                .build();
        return pautaRepository.save(pauta);
    }

    @Override
    public List<PautaDTO> listarPautas() {
        return pautaRepository.findAll().stream()
                .map(pauta -> {
                    PautaDTO dto = new PautaDTO();
                    dto.setTitulo(pauta.getTitulo());
                    dto.setDescricao(pauta.getDescricao());
                    return dto;
                })
                .toList();
    }

    @Override
    public Pauta buscarPorId (Long id){
        return pautaRepository.findById(id)
                .orElseThrow(() -> new PautaNotFoundException("Pauta com id " + id + " não encontrada"));
    }

    @Override
    public ResultadoVotacaoDTO getResultadoVotacao(Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new PautaNotFoundException("Pauta com id " + pautaId + " não encontrada"));

        Long totalSim = votoRepository.countByPautaIdAndVoto(pautaId, "SIM");
        Long totalNao = votoRepository.countByPautaIdAndVoto(pautaId, "NAO");

        String resultado = (totalSim > totalNao) ? "APROVADA" : "REPROVADA";

        return  new ResultadoVotacaoDTO(pautaId, totalSim, totalNao, resultado);

    }

}
