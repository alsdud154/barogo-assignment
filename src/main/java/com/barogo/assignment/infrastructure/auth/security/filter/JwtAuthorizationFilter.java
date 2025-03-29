package com.barogo.assignment.infrastructure.auth.security.filter;

import com.barogo.assignment.application.account.TokenProvider;
import com.barogo.assignment.infrastructure.auth.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final UserDetailsService accountDetailsService;

	public JwtAuthorizationFilter(
			TokenProvider tokenProvider, UserDetailsService accountDetailsService) {
		this.jwtProvider = (JwtProvider) tokenProvider;
		this.accountDetailsService = accountDetailsService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = resolveToken(request);

		if (token != null && jwtProvider.isValid(token)) {
			String username = jwtProvider.extractUsername(token);
			UserDetails userDetails = accountDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
	}
}
