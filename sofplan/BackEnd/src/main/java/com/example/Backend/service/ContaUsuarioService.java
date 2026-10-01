package com.example.Backend.service;

import CTI.BackEnd.model.Usuario;
import com.example.Backend.dto.ContaUsuarioDTO;
import com.example.Backend.dto.UsuarioRequest;
import com.example.Backend.repository.ContaUsuarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContaUsuarioService {

    private final ContaUsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ContaUsuarioService(
            ContaUsuarioRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ContaUsuarioDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public ContaUsuarioDTO buscar(Long id) {
        return toDto(obter(id));
    }

    public ContaUsuarioDTO criar(UsuarioRequest request) {
        return salvarNovo(request);
    }

    public ContaUsuarioDTO atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = obter(id);
        String email = normalizar(request.email());
        repository.findByEmailIgnoreCase(email)
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
                });

        usuario.setNome(request.nome().trim());
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        return toDto(repository.save(usuario));
    }

    public void excluir(Long id) {
        repository.delete(obter(id));
    }

    public ContaUsuarioDTO buscarPorEmail(String email) {
        return repository.findByEmailIgnoreCase(normalizar(email))
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
    }

    private ContaUsuarioDTO salvarNovo(UsuarioRequest request) {
        String email = normalizar(request.email());
        if (repository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome().trim());
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        return toDto(repository.save(usuario));
    }

    private Usuario obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
    }

    private String normalizar(String email) {
        return email.trim().toLowerCase();
    }

    private ContaUsuarioDTO toDto(Usuario usuario) {
        return new ContaUsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}