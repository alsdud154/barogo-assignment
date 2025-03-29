package com.barogo.assignment.infrastructure.auth.security;

import com.barogo.assignment.application.account.TokenProvider;
import com.barogo.assignment.infrastructure.auth.security.filter.JwtAuthorizationFilter;
import com.barogo.assignment.infrastructure.auth.security.userdetails.CustomDetailsService;
import com.barogo.assignment.infrastructure.persistence.jpa.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	private final AccountJpaRepository accountRepository;
	private final TokenProvider tokenProvider;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) ->
				web.ignoring()
						.requestMatchers(
								"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/resources/**");
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(CsrfConfigurer::disable)
				.sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						it ->
								it.requestMatchers("/error")
										.permitAll()
										.requestMatchers(HttpMethod.POST, "/v1/account/sign-up", "/v1/account/sign-in")
										.permitAll()
										.anyRequest()
										.authenticated())
				.userDetailsService(userDetailsService())
				.addFilterBefore(
						new JwtAuthorizationFilter(tokenProvider, userDetailsService()),
						UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomDetailsService(accountRepository);
	}
}
