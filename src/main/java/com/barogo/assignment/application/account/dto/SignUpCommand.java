package com.barogo.assignment.application.account.dto;

import com.barogo.assignment.domain.account.Account;

public record SignUpCommand(String username, String password, String name) {
	public Account toDomain(String encodedPassword) {
		return Account.create(username, encodedPassword, name);
	}
}
