package com.barogo.assignment.application.account;

import com.barogo.assignment.application.ClientException;

public class AlreadyExistsAccountException extends ClientException {
	public AlreadyExistsAccountException() {
		super("이미 가입된 아이디입니다.");
	}
}
