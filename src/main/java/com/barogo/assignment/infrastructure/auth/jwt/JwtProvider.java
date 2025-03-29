package com.barogo.assignment.infrastructure.auth.jwt;

import com.barogo.assignment.application.account.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtProvider implements TokenProvider {

	private final SecretKey secretKey;

	public JwtProvider(String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	@Override
	public String createAccessToken(long id, String username, String name) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + 1000 * 60 * 60);

		return Jwts.builder()
				.subject(username)
				.claim("id", id)
				.claim("name", name)
				.issuedAt(now)
				.expiration(expiration)
				.signWith(secretKey, Jwts.SIG.HS256)
				.compact();
	}

	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
