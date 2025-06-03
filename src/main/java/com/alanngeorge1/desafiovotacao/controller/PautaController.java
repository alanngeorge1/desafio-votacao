package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.alanngeorge1.desafiovotacao.entity.Pauta;
import com.alanngeorge1.desafiovotacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public Pauta criarPauta(@RequestBody PautaDTO pautaDTO) {
        return pautaService.criarPauta(pautaDTO);
    }

    @GetMapping
    public List<PautaDTO> listarPautas() {
        return pautaService.listarPautas();

    }
}
