package com.alanngeorge1.desafiovotacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CpfValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCpfValidoSemMascara() throws Exception {
        mockMvc.perform(get("/api/cpf/12345678901")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.is("ABLE_TO_VOTE"),
                        org.hamcrest.Matchers.is("UNABLE_TO_VOTE")
                )));
    }

    @Test
    public void testCpfValidoComMascara() throws Exception {
        mockMvc.perform(get("/api/cpf/123.456.789-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.is("ABLE_TO_VOTE"),
                        org.hamcrest.Matchers.is("UNABLE_TO_VOTE")
                )));
    }

    @Test
    public void testCpfComCaracteresInvalidos() throws Exception {
        mockMvc.perform(get("/api/cpf/123.456.789-AB")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CPF inválido"));
    }

    @Test
    public void testCpfComDigitosInsuficientes() throws Exception {
        mockMvc.perform(get("/api/cpf/12345678")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CPF inválido"));
    }

    @Test
    public void testCpfBodyComEspacoInvalido() throws Exception {

        String payload = """
        {
            "cpf": "111.111.111-12 "
        }
        """;

        mockMvc.perform(post("/api/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("CPF inválido"));
    }

    @Test
    public void testCpfBodyValidoComMascara() throws Exception {

        String payload = """
        {
            "cpf": "123.456.789-01"
        }
        """;

        mockMvc.perform(post("/api/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.is("ABLE_TO_VOTE"),
                        org.hamcrest.Matchers.is("UNABLE_TO_VOTE")
                )));
    }

    @Test
    public void testCpfBodyValidoSemMascara() throws Exception {

        String payload = """
        {
            "cpf": "12345678901"
        }
        """;

        mockMvc.perform(post("/api/cpf/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.is("ABLE_TO_VOTE"),
                        org.hamcrest.Matchers.is("UNABLE_TO_VOTE")
                )));
    }
}
