package com.barogo.assignment.application.delivery;

import com.barogo.assignment.application.delivery.dto.ChangeAddressCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesResult;
import com.barogo.assignment.domain.delivery.Delivery;
import com.barogo.assignment.infrastructure.persistence.jpa.DeliveryJpaRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DeliveryService {
	private final DeliveryJpaRepository deliveryRepository;

	public Page<GetDeliveriesResult> getDeliveries(GetDeliveriesCommand command) {
		Page<Delivery> deliveries =
				deliveryRepository.findByCreatedAtBetween(
						command.id(),
						LocalDateTime.of(command.startDate(), LocalTime.MIN),
						LocalDateTime.of(command.endDate(), LocalTime.MAX),
						command.pageable());
		return deliveries.map(
				it ->
						new GetDeliveriesResult(
								it.getId(), it.getAddress(), it.getStatus(), it.getCreatedAt()));
	}

	@Transactional
	public void changeAddress(ChangeAddressCommand command) {
		Delivery delivery =
				deliveryRepository.findById(command.id()).orElseThrow(NotFoundDeliveryException::new);

		if (delivery.isNotOwner(command.accountId())) {
			throw new NotFoundDeliveryException();
		}

		if (delivery.canNotChangeAddress()) {
			throw new CanNotChangeAddressException();
		}

		delivery.changeAddress(command.address());
	}
}
