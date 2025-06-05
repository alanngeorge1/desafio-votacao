package com.alanngeorge1.desafiovotacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CpfValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void deveAceitarCpfValidoSemMascara_GET() throws Exception {
        mockMvc.perform(get("/api/v1/cpf/12345678901")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(anyOf(
                        is("ABLE_TO_VOTE"),
                        is("UNABLE_TO_VOTE")
                )));
    }

    @Test
    public void deveAceitarCpfValidoComMascara_GET() throws Exception {
        mockMvc.perform(get("/api/v1/cpf/123.456.789-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(anyOf(
                        is("ABLE_TO_VOTE"),
                        is("UNABLE_TO_VOTE")
                )));
    }

    @Test
    public void deveRejeitarCpfComCaracteresInvalidos_POST() throws Exception {
        String payload = """
        {
            "cpf": "123.456.789-AB"
        }
        """;

        mockMvc.perform(post("/api/v1/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CPF inválido"));
    }

    @Test
    public void deveRejeitarCpfComDigitosInsuficientes_POST() throws Exception {
        String payload = """
        {
            "cpf": "12345678"
        }
        """;

        mockMvc.perform(post("/api/v1/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CPF inválido"));
    }

    @Test
    public void deveRejeitarCpfComEspacoInvalido_POST() throws Exception {
        String payload = """
        {
            "cpf": "111.111.111-12 "
        }
        """;

        mockMvc.perform(post("/api/v1/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CPF inválido"));
    }

    @Test
    public void deveAceitarCpfValidoComMascara_POST() throws Exception {
        String payload = """
        {
            "cpf": "123.456.789-01"
        }
        """;

        mockMvc.perform(post("/api/v1/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(anyOf(
                        is("ABLE_TO_VOTE"),
                        is("UNABLE_TO_VOTE")
                )));
    }

    @Test
    public void deveAceitarCpfValidoSemMascara_POST() throws Exception {
        String payload = """
        {
            "cpf": "12345678901"
        }
        """;

        mockMvc.perform(post("/api/v1/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(anyOf(
                        is("ABLE_TO_VOTE"),
                        is("UNABLE_TO_VOTE")
                )));
    }
}
