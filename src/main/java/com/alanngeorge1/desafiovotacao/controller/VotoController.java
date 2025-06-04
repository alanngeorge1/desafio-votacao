package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.dto.VotoDto;
import com.alanngeorge1.desafiovotacao.entity.Voto;
import com.alanngeorge1.desafiovotacao.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping
    public Voto registrarVoto(@Valid @RequestBody VotoDto votoDto) {
        return votoService.registrarVoto(votoDto);
    }
}
