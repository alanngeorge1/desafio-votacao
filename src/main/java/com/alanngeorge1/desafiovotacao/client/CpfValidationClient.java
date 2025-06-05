package com.alanngeorge1.desafiovotacao.client;

import com.alanngeorge1.desafiovotacao.exception.CpfInvalidoException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfValidationClient {

    public String validarCpf(String cpf) {

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new CpfInvalidoException("CPF inválido");
        }

        if (!cpf.matches("^(\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})|(\\d{11})$")) {
            throw new CpfInvalidoException("CPF inválido");
        }

        String cleanCpf = cpf.replaceAll("[^0-9]", "");

        if (cleanCpf.length() != 11) {
            throw new CpfInvalidoException("CPF inválido");
        }

        boolean habilitadoParaVotar = new Random().nextBoolean();

        if (habilitadoParaVotar) {
            return "ABLE_TO_VOTE";
        } else {
            return "UNABLE_TO_VOTE";
        }
    }
}
