package com.example.Backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ConsultorRequest(
        @NotBlank String nome,
        @NotBlank String matricula,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.") String senha) {
}