package com.barogo.assignment.application.account;

import com.barogo.assignment.application.account.dto.SignInCommand;
import com.barogo.assignment.application.account.dto.SignInResult;
import com.barogo.assignment.application.account.dto.SignUpCommand;
import com.barogo.assignment.domain.account.Account;
import com.barogo.assignment.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {
	private final AccountRepository accountRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	@Transactional
	public void signUp(SignUpCommand command) {
		accountRepository
				.findByUsername(command.username())
				.ifPresent(
						user -> {
							throw new AlreadyExistsAccountException();
						});

		String encodedPassword = passwordEncoder.encode(command.password());
		Account createAccount = command.toDomain(encodedPassword);

		accountRepository.save(createAccount);
	}

	public SignInResult signIn(SignInCommand command) {
		Account account =
				accountRepository
						.findByUsername(command.username())
						.orElseThrow(InvalidUsernamePasswordException::new);

		if (!passwordEncoder.matches(command.password(), account.getPassword())) {
			throw new InvalidUsernamePasswordException();
		}

		String accessToken =
				tokenProvider.createAccessToken(account.getId(), account.getUsername(), account.getName());

		return new SignInResult(accessToken);
	}
}
