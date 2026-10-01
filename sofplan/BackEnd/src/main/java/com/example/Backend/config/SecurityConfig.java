package com.example.Backend.config;

import java.util.List;
import com.example.Backend.repository.ContaUsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
				.securityContext(context -> context.securityContextRepository(securityContextRepository()))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/login", "/api/importacao").permitAll()
						.requestMatchers(org.springframework.http.HttpMethod.POST, "/api/usuarios").permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(errors -> errors.authenticationEntryPoint(
						(request, response, exception) -> response.sendError(401)));

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	UserDetailsService userDetailsService(ContaUsuarioRepository repository) {
		return email -> repository.findByEmailIgnoreCase(email)
				.map(usuario -> User.withUsername(usuario.getEmail())
						.password(usuario.getSenha())
						.roles("USER")
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
	}

	@Bean
	SecurityContextRepository securityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of(
				"http://localhost:*",
				"http://127.0.0.1:*"
		));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		return source;
	}
}
