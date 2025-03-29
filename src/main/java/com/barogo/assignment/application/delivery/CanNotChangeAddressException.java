package com.barogo.assignment.application.delivery;

import com.barogo.assignment.application.ClientException;

public class CanNotChangeAddressException extends ClientException {
	public CanNotChangeAddressException() {
		super("주소를 변경할 수 없는 상태입니다.");
	}
}
