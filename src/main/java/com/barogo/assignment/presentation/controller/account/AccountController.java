package com.barogo.assignment.presentation.controller.account;

import com.barogo.assignment.application.account.AccountService;
import com.barogo.assignment.application.account.dto.SignInResult;
import com.barogo.assignment.presentation.controller.account.dto.SignInRequest;
import com.barogo.assignment.presentation.controller.account.dto.SignInResponse;
import com.barogo.assignment.presentation.controller.account.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자")
@RequiredArgsConstructor
@RestController
public class AccountController {
	private final AccountService accountService;

	@PostMapping("/v1/account/sign-up")
	@Operation(summary = "회원가입", description = "사용자 계정을 생성합니다.")
	public void signUp(@RequestBody @Validated SignUpRequest request) {
		accountService.signUp(request.toCommand());
	}

	@PostMapping("/v1/account/sign-in")
	@Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인합니다.")
	public SignInResponse signIn(@RequestBody @Validated SignInRequest request) {
		SignInResult result = accountService.signIn(request.toCommand());

		return SignInResponse.toResponse(result);
	}
}
