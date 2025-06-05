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
public class ResultadoVotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long pautaId;

    @BeforeEach
    public void setup() throws Exception {

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Pauta para Teste de Resultado");
        pautaDTO.setDescricao("Descrição da pauta");

        String pautaResponse = mockMvc.perform(post("/api/v1/pautas")
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

        mockMvc.perform(post("/api/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isOk());

        VotoDTO votoSim = new VotoDTO();
        votoSim.setPautaId(pautaId);
        votoSim.setAssociadoId("11111111111");
        votoSim.setVoto("SIM");

        mockMvc.perform(post("/api/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoSim)))
                .andExpect(status().isOk());

        VotoDTO votoNao = new VotoDTO();
        votoNao.setPautaId(pautaId);
        votoNao.setAssociadoId("22222222222");
        votoNao.setVoto("NAO");

        mockMvc.perform(post("/api/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoNao)))
                .andExpect(status().isOk());
    }

    @Test
    public void testResultadoVotacao() throws Exception {
        mockMvc.perform(get("/api/v1/pautas/" + pautaId + "/resultado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pautaId").value(pautaId))
                .andExpect(jsonPath("$.totalSim").value(1))
                .andExpect(jsonPath("$.totalNao").value(1))
                .andExpect(jsonPath("$.resultado").exists());
    }
}
