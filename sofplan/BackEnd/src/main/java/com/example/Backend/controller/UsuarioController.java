package com.example.Backend.controller;

import com.example.Backend.dto.ConsultorRequest;
import com.example.Backend.dto.ContaUsuarioDTO;
import com.example.Backend.dto.LoginRequest;
import com.example.Backend.dto.UsuarioRequest;
import com.example.Backend.dto.UsuarioDTO;
import com.example.Backend.service.ContaUsuarioService;
import com.example.Backend.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	private final UsuarioService service;
	private final ContaUsuarioService contaUsuarioService;
	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository;

	public UsuarioController(
			UsuarioService service,
			ContaUsuarioService contaUsuarioService,
			AuthenticationManager authenticationManager,
			SecurityContextRepository securityContextRepository) {
		this.service = service;
		this.contaUsuarioService = contaUsuarioService;
		this.authenticationManager = authenticationManager;
		this.securityContextRepository = securityContextRepository;
	}

	@PostMapping("/auth/login")
	public ContaUsuarioDTO login(
			@Valid @RequestBody LoginRequest request,
			HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.senha()));
		} catch (AuthenticationException exception) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos.");
		}
		if (httpRequest.getSession(false) != null) {
			httpRequest.changeSessionId();
		}
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		securityContextRepository.saveContext(context, httpRequest, httpResponse);
		return contaUsuarioService.buscarPorEmail(request.email());
	}

	@GetMapping("/usuarios")
	public List<ContaUsuarioDTO> listarUsuarios() {
		return contaUsuarioService.listar();
	}

	@GetMapping("/usuarios/{id}")
	public ContaUsuarioDTO buscarUsuario(@PathVariable Long id) {
		return contaUsuarioService.buscar(id);
	}

	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ContaUsuarioDTO criarUsuario(@Valid @RequestBody UsuarioRequest request) {
		return contaUsuarioService.criar(request);
	}

	@PutMapping("/usuarios/{id}")
	public ContaUsuarioDTO atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
		return contaUsuarioService.atualizar(id, request);
	}

	@DeleteMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirUsuario(@PathVariable Long id) {
		contaUsuarioService.excluir(id);
	}

	@GetMapping("/consultores")
	public List<UsuarioDTO> listar() {
		return service.listar();
	}

	@GetMapping("/consultores/{id}")
	public UsuarioDTO buscar(@PathVariable Long id) {
		return service.buscar(id);
	}

	@PostMapping("/consultores")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO criar(@Valid @RequestBody ConsultorRequest request) {
		return service.criar(request);
	}

	@PutMapping("/consultores/{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @Valid @RequestBody ConsultorRequest request) {
		return service.atualizar(id, request);
	}

	@DeleteMapping("/consultores/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		service.excluir(id);
	}
}
