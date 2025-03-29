package com.barogo.assignment.presentation.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordValidatorTest {

	private final PasswordValidator validator = new PasswordValidator();

	@Test
	void 조건_3종_충족시_true() {
		// 대문자 + 숫자 + 특수문자
		assertTrue(validator.isValid("Abcdefghij1!", null));

		// 소문자 + 숫자 + 특수문자
		assertTrue(validator.isValid("abcdefg1234!", null));

		// 대문자 + 소문자 + 숫자
		assertTrue(validator.isValid("Abcdefgh1234", null));
	}

	@Test
	void 조건_2종만_충족시_false() {
		assertFalse(validator.isValid("abcdefghijk1", null)); // 소문자 + 숫자
		assertFalse(validator.isValid("ABCDEFGH!@#", null)); // 대문자 + 특수문자
		assertFalse(validator.isValid("1234567890!@", null)); // 숫자 + 특수문자
	}

	@Test
	void 길이_부족시_false() {
		assertFalse(validator.isValid("Abc1!", null)); // 조건 충족하더라도 길이 부족
	}

	@Test
	void null_값은_false() {
		assertFalse(validator.isValid(null, null));
	}

	@Test
	void 조건_4종_모두_충족해도_길이_부족하면_false() {
		assertFalse(validator.isValid("Ab1!c", null)); // 5자
	}

	@Test
	void 조건_4종_충족_길이_충족하면_true() {
		assertTrue(validator.isValid("Abcdef1!@#456", null));
	}
}
