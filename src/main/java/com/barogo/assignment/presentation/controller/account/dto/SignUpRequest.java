package com.barogo.assignment.presentation.controller.account.dto;

import com.barogo.assignment.application.account.dto.SignUpCommand;
import com.barogo.assignment.presentation.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
		@Schema(description = "아이디", example = "barogo") @NotBlank String username,
		@Schema(description = "비밀번호", example = "test1234!!!!") @ValidPassword String password,
		@Schema(description = "이름", example = "바로고") @NotBlank String name) {
	public SignUpCommand toCommand() {
		return new SignUpCommand(username, password, name);
	}
}
