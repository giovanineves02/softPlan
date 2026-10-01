package com.example.Backend.dto;

import java.util.List;
import java.util.Map;

public record ImportacaoRequest(String nomeArquivo, List<Map<String, Object>> dados) {
}