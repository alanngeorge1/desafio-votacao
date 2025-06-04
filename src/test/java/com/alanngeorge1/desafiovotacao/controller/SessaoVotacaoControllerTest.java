package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.alanngeorge1.desafiovotacao.dto.SessaoVotacaoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessaoVotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private long pautaId;


    @BeforeEach
    public void setup() throws  Exception {

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Pauta para Sessão Teste");
        pautaDTO.setTitulo("Descrição da pauta para sessão");

        String response  = mockMvc.perform(post("/api/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pautaDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        pautaId = objectMapper.readTree(response).get("id").asLong();
    }


    @Test
    public void testAbrirSessaoVotacao() throws Exception {

        SessaoVotacaoDTO sessaoVotacaoDTO = new SessaoVotacaoDTO();
        sessaoVotacaoDTO.setPautaId(pautaId);
        sessaoVotacaoDTO.setDuracaoEmMinutos(5);

        mockMvc.perform(post("/api/sessoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessaoVotacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pauta.id").value(pautaId))
                .andExpect(jsonPath("$.dataHoraInicio").exists())
                .andExpect(jsonPath("$.dataHoraFim").exists());

    }

    @Test
    public void testAbrirSessaoComPautaInexistente() throws Exception {
        SessaoVotacaoDTO sessaoDTO = new SessaoVotacaoDTO();
        sessaoDTO.setPautaId(99999L);  // ID inexistente
        sessaoDTO.setDuracaoEmMinutos(5);

        mockMvc.perform(post("/api/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Pauta não encontrada"));
    }
}
