package com.alanngeorge1.desafiovotacao.performance;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VotoPerformanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long pautaId;

    @BeforeEach
    public void setup() throws Exception {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Pauta para Stress Test");
        pautaDTO.setDescricao("Teste de performance com muitos votos");

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
        sessaoDTO.setDuracaoEmMinutos(10);

        mockMvc.perform(post("/api/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegistrarMuitosVotos() throws Exception {
        int quantidadeVotos = 1000;

        for (int i = 1; i <= quantidadeVotos; i++) {
            VotoDTO voto = new VotoDTO();
            voto.setPautaId(pautaId);
            voto.setAssociadoId("CPF" + i);
            voto.setVoto(i % 2 == 0 ? "SIM" : "NAO");

            mockMvc.perform(post("/api/v1/votos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(voto)))
                    .andExpect(status().isOk());
        }

    }
}
