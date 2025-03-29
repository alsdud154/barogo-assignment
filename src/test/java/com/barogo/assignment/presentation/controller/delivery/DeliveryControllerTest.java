package com.barogo.assignment.presentation.controller.delivery;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.barogo.assignment.application.delivery.DeliveryService;
import com.barogo.assignment.application.delivery.dto.ChangeAddressCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesResult;
import com.barogo.assignment.domain.delivery.DeliveryStatus;
import com.barogo.assignment.infrastructure.auth.security.userdetails.AccountDetails;
import com.barogo.assignment.presentation.controller.delivery.dto.ChangeAddressRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DeliveryController.class)
public class DeliveryControllerTest {

	@Autowired private MockMvc mockMvc;

	@MockitoBean private DeliveryService deliveryService;

	@BeforeEach
	void beforeEach() {
		AccountDetails accountDetails = AccountDetails.create(1L, "barogo", "바르고");

		Authentication authentication =
				new UsernamePasswordAuthenticationToken(
						accountDetails, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	@AfterEach
	void afterEach() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void 배달_조회_성공시_200() throws Exception {
		// given
		List<GetDeliveriesResult> deliveries =
				List.of(new GetDeliveriesResult(1L, "주소1", DeliveryStatus.PENDING, LocalDateTime.now()));
		Page<GetDeliveriesResult> page =
				new PageImpl<>(deliveries, PageRequest.of(0, 10), deliveries.size());
		given(deliveryService.getDeliveries(any(GetDeliveriesCommand.class))).willReturn(page);

		// when & then
		mockMvc
				.perform(
						get("/v1/deliveries")
								.param("startDate", "2025-03-01")
								.param("endDate", "2025-03-02")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id").value(1L))
				.andExpect(jsonPath("$.content[0].address").value("주소1"))
				.andExpect(jsonPath("$.content[0].status").value("PENDING"));

		// verify
		verify(deliveryService, times(1)).getDeliveries(any(GetDeliveriesCommand.class));
	}

	@Test
	public void 배달_조회_시작일이_종료일보다_늦은_경우_400() throws Exception {
		// when & then
		mockMvc
				.perform(
						get("/v1/deliveries")
								.param("startDate", "2025-03-03")
								.param("endDate", "2025-03-01")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("시작일은 종료일보다 클 수 없습니다."));
	}

	@Test
	public void 배달_조회_기간이_3일_초과하면_400() throws Exception {
		// when & then
		mockMvc
				.perform(
						get("/v1/deliveries")
								.param("startDate", "2025-03-01")
								.param("endDate", "2025-03-05")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("조회 가능한 기간은 최대 3일 입니다."));
	}

	@Test
	public void 배달_조회_날짜_누락_시_400() throws Exception {
		// when & then
		mockMvc
				.perform(
						get("/v1/deliveries")
								.param("endDate", "2025-03-01") // startDate 없음
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("시작일을 입력해주세요."));

		mockMvc
				.perform(
						get("/v1/deliveries")
								.param("startDate", "2025-03-01") // endDate 없음
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("종료일을 입력해주세요."));
	}

	@Test
	public void 배달_조회_미래_날짜인_경우_400() throws Exception {
		// given
		LocalDate futureDate = LocalDate.now().plusDays(1);

		// when & then
		mockMvc
				.perform(
						get("/v1/deliveries")
								.param("startDate", futureDate.toString())
								.param("endDate", futureDate.toString())
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("미래의 날짜는 입력할 수 없습니다."));
	}

	@Test
	public void 배달_주소_변경_성공시_200() throws Exception {
		// given
		long deliveryId = 1L;
		ChangeAddressRequest request = new ChangeAddressRequest("새로운 주소");

		ChangeAddressCommand command = request.toCommand(deliveryId, 1L);

		// when & then
		mockMvc
				.perform(
						patch("/v1/deliveries/{id}", deliveryId)
								.with(csrf())
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isOk());

		// verify
		verify(deliveryService, times(1)).changeAddress(command);
	}
}
