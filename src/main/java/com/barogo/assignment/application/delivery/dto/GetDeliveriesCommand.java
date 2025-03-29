package com.barogo.assignment.application.delivery.dto;

import java.time.LocalDate;
import org.springframework.data.domain.Pageable;

public record GetDeliveriesCommand(
		long id, LocalDate startDate, LocalDate endDate, Pageable pageable) {}
