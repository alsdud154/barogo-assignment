package com.barogo.assignment.application.account;

import com.barogo.assignment.application.ClientException;

public class InvalidUsernamePasswordException extends ClientException {
	public InvalidUsernamePasswordException() {
		super("아이디와 비밀번호를 확인해주세요.");
	}
}
