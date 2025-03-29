package com.barogo.assignment.presentation.controller.account;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.barogo.assignment.application.account.AccountService;
import com.barogo.assignment.application.account.dto.SignUpCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

	@Autowired private MockMvc mockMvc;

	@MockitoBean private AccountService accountService;

	@Test
	void 회원가입_성공시_200() throws Exception {
		// given
		String payload =
				"""
					{
					"username": "min123",
					"password": "Password123!",
					"name": "민영"
					}
				""";

		// when & then
		mockMvc
				.perform(
						post("/v1/account/sign-up").contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(status().isOk());

		// verify
		verify(accountService).signUp(any(SignUpCommand.class));
	}

	@Test
	void 유효하지_않은_요청이면_400() throws Exception {
		// given
		String payload =
				"""
					{
					"username": "min123",
					"name": "민영"
					}
				""";

		// when & then
		mockMvc
				.perform(
						post("/v1/account/sign-up").contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(status().isBadRequest());
	}
}
