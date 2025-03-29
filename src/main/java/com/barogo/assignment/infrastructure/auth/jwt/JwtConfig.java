package com.barogo.assignment.infrastructure.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class JwtConfig {

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Bean
	public JwtProvider tokenProvider() {
		return new JwtProvider(secretKey);
	}
}
