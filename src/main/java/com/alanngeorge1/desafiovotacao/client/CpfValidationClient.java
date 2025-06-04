package com.alanngeorge1.desafiovotacao.client;

import com.alanngeorge1.desafiovotacao.exception.CpfInvalidoException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfValidationClient {

    private final Random random = new Random();

    public String validarCpf(String cpf) {

        cpf = cpf.trim();

        if (!isValidCpfFormat(cpf)) {
            throw new CpfInvalidoException("CPF inválido (formato ou dígitos incorretos): " + cpf);
        }

        boolean cpfValido = random.nextInt(10) < 8;

        if (!cpfValido) {
            throw new CpfInvalidoException("CPF inválido (simulação aleatória): " + cpf);
        }

        boolean podeVotar = random.nextBoolean();

        return podeVotar ? "ABLE_TO_VOTE" : "UNABLE_TO_VOTE";
    }

    private boolean isValidCpfFormat(String cpf) {

        String regexSemMascara = "\\d{11}";

        String regexComMascara = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

        return cpf.matches(regexSemMascara) || cpf.matches(regexComMascara);
    }
}