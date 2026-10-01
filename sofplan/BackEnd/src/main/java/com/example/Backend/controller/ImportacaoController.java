package com.example.Backend.controller;

import com.example.Backend.dto.ImportacaoRequest;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/importacao")
public class ImportacaoController {

    @PostMapping
    public ResponseEntity<Map<String, Object>> importar(@RequestBody ImportacaoRequest request) {
        if (request.dados() == null || request.dados().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "sucesso", false,
                    "mensagem", "A importação deve conter pelo menos um registro."
            ));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "sucesso", true,
                "mensagem", "Dados recebidos pelo backend.",
                "nomeArquivo", request.nomeArquivo() == null ? "" : request.nomeArquivo(),
                "totalRegistros", request.dados().size()
        ));
    }
}