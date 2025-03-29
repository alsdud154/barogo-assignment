package com.barogo.assignment.infrastructure.auth.security.userdetails;

import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDetails implements UserDetails {
	@Getter private final long id;
	private final String username;
	@Getter private final String name;

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public static AccountDetails create(long id, String username, String name) {
		return new AccountDetails(id, username, name);
	}
}
