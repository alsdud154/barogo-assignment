package com.barogo.assignment.application.delivery.dto;

import com.barogo.assignment.domain.delivery.DeliveryStatus;
import java.time.LocalDateTime;

public record GetDeliveriesResult(
		long id, String address, DeliveryStatus status, LocalDateTime createdAt) {}
