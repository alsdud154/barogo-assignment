package com.barogo.assignment.infrastructure.persistence.jpa;

import com.barogo.assignment.domain.delivery.Delivery;
import com.barogo.assignment.domain.delivery.DeliveryRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, Long>, DeliveryRepository {
	@Query(
			"select d from Delivery d where d.account.id = :id and d.createdAt >= :startAt and d.createdAt <= :endAt")
	Page<Delivery> findByCreatedAtBetween(
			long id, LocalDateTime startAt, LocalDateTime endAt, Pageable pageable);

	@EntityGraph(attributePaths = {"account"})
	Optional<Delivery> findById(long id);
}
