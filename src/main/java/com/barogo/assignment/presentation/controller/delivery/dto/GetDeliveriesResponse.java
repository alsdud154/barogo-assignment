package com.barogo.assignment.presentation.controller.delivery.dto;

import com.barogo.assignment.domain.delivery.DeliveryStatus;
import java.time.LocalDateTime;

public record GetDeliveriesResponse(
		long id, String address, DeliveryStatus status, LocalDateTime createdAt) {}
