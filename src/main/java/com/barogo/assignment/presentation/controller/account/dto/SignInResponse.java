package com.barogo.assignment.presentation.controller.account.dto;

import com.barogo.assignment.application.account.dto.SignInResult;

public record SignInResponse(String accessToken) {
	public static SignInResponse toResponse(SignInResult result) {
		return new SignInResponse(result.accessToken());
	}
}
