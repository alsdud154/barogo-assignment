package com.barogo.assignment.application.delivery;

import com.barogo.assignment.application.ClientException;

public class NotFoundDeliveryException extends ClientException {
	public NotFoundDeliveryException() {
		super("배달 정보가 없습니다.");
	}
}
