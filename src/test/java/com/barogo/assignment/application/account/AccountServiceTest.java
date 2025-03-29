package com.barogo.assignment.application.account;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.barogo.assignment.application.account.dto.SignUpCommand;
import com.barogo.assignment.domain.account.Account;
import com.barogo.assignment.domain.account.AccountRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock private AccountRepository accountRepository;

	@Mock private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks private AccountService accountService;

	FixtureMonkey fixture =
			FixtureMonkey.builder()
					.objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
					.build();

	@Test
	void 회원가입_성공_시_저장된다() {
		// given
		SignUpCommand command =
				fixture.giveMeBuilder(SignUpCommand.class).set("password", "test1234!!!").sample();

		String encodedPassword = "encodedPassword";
		Account account = command.toDomain(encodedPassword);

		given(accountRepository.findByUsername(any())).willReturn(Optional.empty());
		given(passwordEncoder.encode(any())).willReturn(encodedPassword);
		given(accountRepository.save(any(Account.class))).willReturn(account);

		// when
		accountService.signUp(command);

		// verify
		verify(accountRepository).save(any(Account.class));
	}

	@Test
	void 이미_존재하는_아이디면_예외를_던진다() {
		// given
		SignUpCommand command =
				fixture.giveMeBuilder(SignUpCommand.class).set("password", "test1234!!!").sample();

		given(accountRepository.findByUsername(any())).willReturn(Optional.of(mock(Account.class)));

		// when & then
		assertThrows(AlreadyExistsAccountException.class, () -> accountService.signUp(command));

		// verify
		verify(passwordEncoder, never()).encode(any());
		verify(accountRepository, never()).save(any());
	}
}
