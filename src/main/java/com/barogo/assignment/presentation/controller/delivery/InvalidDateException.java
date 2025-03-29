package com.barogo.assignment.presentation.controller.delivery;

import com.barogo.assignment.application.ClientException;

public class InvalidDateException extends ClientException {
	public InvalidDateException(String message) {
		super(message);
	}
}
