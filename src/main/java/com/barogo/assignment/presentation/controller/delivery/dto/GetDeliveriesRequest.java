package com.barogo.assignment.presentation.controller.delivery.dto;

import com.barogo.assignment.application.delivery.dto.GetDeliveriesCommand;
import com.barogo.assignment.presentation.controller.delivery.InvalidDateException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;

public record GetDeliveriesRequest(
		@Schema(description = "시작일", example = "2025-03-25") @NotNull LocalDate startDate,
		@Schema(description = "종료일", example = "2025-03-27") @NotNull LocalDate endDate) {

	public GetDeliveriesCommand toCommand(long id, Pageable pageable) {
		return new GetDeliveriesCommand(id, startDate, endDate, pageable);
	}

	public void validateDateRange() {
		LocalDate nowDate = LocalDate.now();
		if (nowDate.isBefore(startDate) || nowDate.isBefore(endDate)) {
			throw new InvalidDateException("미래의 날짜는 입력할 수 없습니다.");
		}

		if (startDate.isAfter(endDate)) {
			throw new InvalidDateException("시작일은 종료일보다 클 수 없습니다.");
		}

		if (startDate.until(endDate).getDays() >= 3) {
			throw new InvalidDateException("조회 가능한 기간은 최대 3일 입니다.");
		}
	}
}
