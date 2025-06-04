package com.alanngeorge1.desafiovotacao.controller;

import com.alanngeorge1.desafiovotacao.client.CpfValidationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cpf")
public class CpfValidationController {

    @Autowired
    private CpfValidationClient cpfValidationClient;

    @GetMapping("/{cpf}")
    public ResponseEntity<Map<String, String>> validarCpf(@PathVariable String cpf) {

        String status = cpfValidationClient.validarCpf(cpf);

        Map<String, String> response = new HashMap<>();
        response.put("status", status);

        return ResponseEntity.ok(response);
    }
}
