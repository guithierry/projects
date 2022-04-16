package com.backend.configs;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.repositories.UserRepository;
import com.backend.services.AuthenticationService;
import com.backend.entities.User;

public class AuthenticationFilter extends OncePerRequestFilter {

	private AuthenticationService authenticationService;
	private UserRepository userRepository;

	public AuthenticationFilter(AuthenticationService authenticationService, UserRepository userRepository) {
		this.authenticationService = authenticationService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = this.getValidToken(request);
		boolean isValid = this.authenticationService.verifyToken(token);

		if (isValid) {
			this.authencaticateUser(token);
		}

		filterChain.doFilter(request, response);
	}

	private String getValidToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

	private void authencaticateUser(String token) {
		String id = this.authenticationService.getUserId(token);

		User user = this.userRepository.findById(UUID.fromString(id)).get();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
