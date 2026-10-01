package com.example.Backend.service;

import CTI.BackEnd.model.Consultor;
import com.example.Backend.dto.ConsultorRequest;
import com.example.Backend.dto.UsuarioDTO;
import com.example.Backend.repository.UsuarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UsuarioDTO> listar() {
		return repository.findAll().stream().map(this::toDto).toList();
	}

	public UsuarioDTO buscar(Long id) {
		return toDto(obterConsultor(id));
	}

	public UsuarioDTO criar(ConsultorRequest request) {
		String email = normalizarEmail(request.email());
		if (repository.existsByEmailIgnoreCase(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
		}

		Consultor consultor = new Consultor();
		preencher(consultor, request, email);
		consultor.setSenha(passwordEncoder.encode(request.senha()));
		return toDto(repository.save(consultor));
	}

	public UsuarioDTO atualizar(Long id, ConsultorRequest request) {
		Consultor consultor = obterConsultor(id);
		String email = normalizarEmail(request.email());
		repository.findByEmailIgnoreCase(email)
				.filter(existente -> !existente.getId().equals(id))
				.ifPresent(existente -> {
					throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
				});

		preencher(consultor, request, email);
		consultor.setSenha(passwordEncoder.encode(request.senha()));
		return toDto(repository.save(consultor));
	}

	public void excluir(Long id) {
		repository.delete(obterConsultor(id));
	}

	public UsuarioDTO buscarPorEmail(String email) {
		Consultor consultor = repository.findByEmailIgnoreCase(normalizarEmail(email))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consultor não encontrado."));
		return toDto(consultor);
	}

	private Consultor obterConsultor(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consultor não encontrado."));
	}

	private void preencher(Consultor consultor, ConsultorRequest request, String email) {
		consultor.setNome(request.nome().trim());
		consultor.setMatricula(request.matricula().trim());
		consultor.setEmail(email);
	}

	private String normalizarEmail(String email) {
		return email.trim().toLowerCase();
	}

	private UsuarioDTO toDto(Consultor consultor) {
		return new UsuarioDTO(consultor.getId(), consultor.getNome(), consultor.getMatricula(), consultor.getEmail());
	}
}
