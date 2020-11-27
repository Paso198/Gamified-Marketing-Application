package it.polimi.db2.questionnaire.config.jwt;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class JwtSecretKey {
	private final JwtConfig jwtConfig;
		
	@Bean
	public SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}
}
