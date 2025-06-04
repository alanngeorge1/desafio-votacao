package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.alanngeorge1.desafiovotacao.dto.ResultadoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.service.PautaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public Pauta criarPauta(@Valid @RequestBody PautaDTO pautaDTO) {
        return pautaService.criarPauta(pautaDTO);
    }

    @GetMapping()
    public List<PautaDTO> listarPautas() {
        return pautaService.listarPautas();

    }


    @GetMapping("/{id}")
    public PautaDTO buscarPautaPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPorId(id);

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo(pauta.getTitulo());
        pautaDTO.setDescricao(pauta.getDescricao());
        return pautaDTO;
    }

    @GetMapping("/{id}/resultado")
    public ResultadoVotacaoDTO getResultadoVotacao(@PathVariable Long id) {
        return pautaService.getResultadoVotacao(id);
    }
}
