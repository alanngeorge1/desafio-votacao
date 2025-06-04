package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.alanngeorge1.desafiovotacao.dto.SessaoVotacaoDTO;
import com.alanngeorge1.desafiovotacao.dto.VotoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long pautaId;

    @BeforeEach
    public void setup() throws Exception {

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Pauta para Teste de Voto");
        pautaDTO.setDescricao("Descrição da pauta");

        String pautaResponse = mockMvc.perform(post("/api/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        pautaId = objectMapper.readTree(pautaResponse).get("id").asLong();

        SessaoVotacaoDTO sessaoDTO = new SessaoVotacaoDTO();
        sessaoDTO.setPautaId(pautaId);
        sessaoDTO.setDuracaoEmMinutos(5);

        mockMvc.perform(post("/api/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegistrarVotoComSucesso() throws Exception {
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setPautaId(pautaId);
        votoDTO.setAssociadoId("12345678900");
        votoDTO.setVoto("SIM");

        mockMvc.perform(post("/api/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pauta.id").value(pautaId))
                .andExpect(jsonPath("$.associadoId").value("12345678900"))
                .andExpect(jsonPath("$.voto").value("SIM"));
    }

    @Test
    public void testRegistrarVotoDuplicado() throws Exception {
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setPautaId(pautaId);
        votoDTO.setAssociadoId("12345678900");
        votoDTO.setVoto("SIM");

        mockMvc.perform(post("/api/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Associado já votou nesta pauta."));
    }
}
