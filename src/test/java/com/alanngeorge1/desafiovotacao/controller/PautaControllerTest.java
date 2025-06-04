package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.dto.PautaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deveCriarPauta() throws Exception {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Pauta Teste");
        pautaDTO.setDescricao("Descrição da pauta de teste");

        mockMvc.perform(post("/api/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Pauta Teste"))
                .andExpect(jsonPath("$.descricao").value("Descrição da pauta de teste"));
    }

    @Test
    public void testListarPautas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pautas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testBuscarPuataInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pautas/999999", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Pauta não encontrada"));

    }

    @Test
    public void testPautaTituloInvalido() throws Exception {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("");
        pautaDTO.setDescricao("Descrição invalida");

        mockMvc.perform(post("/api/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pautaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro Validação"));
    }
}
