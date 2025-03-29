package com.barogo.assignment.infrastructure.auth.security.userdetails;

import com.barogo.assignment.domain.account.Account;
import com.barogo.assignment.infrastructure.persistence.jpa.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {
	private final AccountJpaRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account =
				accountRepository
						.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		return AccountDetails.create(account.getId(), account.getUsername(), account.getName());
	}
}
