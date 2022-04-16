package com.backend.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.entities.User;
import com.backend.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService implements UserDetailsService {
	
	private final String secret = "secret";
	private final String expiration = "86400000";

	private UserRepository userRepository;
	
	public AuthenticationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = this.userRepository.findByEmail(username);
		
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return user.get();
	}

	public String generateToken(Authentication authenticate) {
		User user = (User) authenticate.getPrincipal();
		String id = user.getId().toString();
		Date now = new Date();
		Date expires = new Date(now.getTime() + Long.parseLong(this.expiration));

		return Jwts
				.builder()
				.setIssuer("token")
				.setSubject(id)
				.setIssuedAt(now)
				.setExpiration(expires)
				.signWith(SignatureAlgorithm.HS256, this.secret)
				.compact();
	}
	
	public boolean verifyToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}
	
	public String hashPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
	
	
}
