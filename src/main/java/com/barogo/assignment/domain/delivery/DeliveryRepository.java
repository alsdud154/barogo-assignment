package com.barogo.assignment.domain.delivery;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRepository {
	Page<Delivery> findByCreatedAtBetween(
			long id, LocalDateTime startAt, LocalDateTime endAt, Pageable pageable);

	Optional<Delivery> findById(long id);
}
