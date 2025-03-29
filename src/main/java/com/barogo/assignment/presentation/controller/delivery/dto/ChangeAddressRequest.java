package com.barogo.assignment.presentation.controller.delivery.dto;

import com.barogo.assignment.application.delivery.dto.ChangeAddressCommand;
import jakarta.validation.constraints.NotBlank;

public record ChangeAddressRequest(@NotBlank String address) {
	public ChangeAddressCommand toCommand(long id, long accountId) {
		return new ChangeAddressCommand(id, accountId, address);
	}
}
