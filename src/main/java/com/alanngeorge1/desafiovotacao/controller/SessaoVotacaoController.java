package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.dto.SessaoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.entity.SessaoVotacao;
import com.alanngeorge1.desafiovotacao.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoVotacaoController {


    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;

    @PostMapping
    public SessaoVotacao abrirSessao(@Valid @RequestBody SessaoVotacaoDTO sessaoVotacaoDTO) {
        return sessaoVotacaoService.abrirSessao(sessaoVotacaoDTO);
    }

}
