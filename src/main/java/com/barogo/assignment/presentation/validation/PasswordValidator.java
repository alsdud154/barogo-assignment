package com.barogo.assignment.presentation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password == null || password.length() < 12) {
			return false;
		}

		boolean hasUpper = false;
		boolean hasLower = false;
		boolean hasDigit = false;
		boolean hasSpecial = false;

		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) hasUpper = true;
			else if (Character.isLowerCase(c)) hasLower = true;
			else if (Character.isDigit(c)) hasDigit = true;
			else hasSpecial = true;
		}

		int passedCount = 0;
		if (hasUpper) passedCount++;
		if (hasLower) passedCount++;
		if (hasDigit) passedCount++;
		if (hasSpecial) passedCount++;

		return passedCount >= 3;
	}
}
