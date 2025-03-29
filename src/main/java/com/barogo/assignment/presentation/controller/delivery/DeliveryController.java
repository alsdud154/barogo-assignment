package com.barogo.assignment.presentation.controller.delivery;

import com.barogo.assignment.application.delivery.DeliveryService;
import com.barogo.assignment.application.delivery.dto.ChangeAddressCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesResult;
import com.barogo.assignment.infrastructure.auth.security.userdetails.AccountDetails;
import com.barogo.assignment.presentation.controller.delivery.dto.ChangeAddressRequest;
import com.barogo.assignment.presentation.controller.delivery.dto.GetDeliveriesRequest;
import com.barogo.assignment.presentation.controller.delivery.dto.GetDeliveriesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "배달")
@RequiredArgsConstructor
@RequestMapping("/v1/deliveries")
@RestController
public class DeliveryController {
	private final DeliveryService deliveryService;

	@Operation(summary = "배달 목록 조회", description = "배달 목록을 조회합니다.")
	@GetMapping
	public Page<GetDeliveriesResponse> getDeliveries(
			@ModelAttribute @Validated GetDeliveriesRequest request,
			@ParameterObject Pageable pageable,
			@AuthenticationPrincipal AccountDetails accountDetails) {
		request.validateDateRange();
		GetDeliveriesCommand command = request.toCommand(accountDetails.getId(), pageable);
		Page<GetDeliveriesResult> result = deliveryService.getDeliveries(command);

		return result.map(
				it -> new GetDeliveriesResponse(it.id(), it.address(), it.status(), it.createdAt()));
	}

	@Operation(summary = "배달 주소 변경", description = "배달 주소를 변경합니다.")
	@PatchMapping("/{id}")
	public void changeAddress(
			@PathVariable long id,
			@RequestBody @Validated ChangeAddressRequest request,
			@AuthenticationPrincipal AccountDetails accountDetails) {
		ChangeAddressCommand command = request.toCommand(id, accountDetails.getId());
		deliveryService.changeAddress(command);
	}
}
