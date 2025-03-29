package com.barogo.assignment.application.delivery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.barogo.assignment.application.delivery.dto.ChangeAddressCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesCommand;
import com.barogo.assignment.application.delivery.dto.GetDeliveriesResult;
import com.barogo.assignment.domain.account.Account;
import com.barogo.assignment.domain.delivery.Delivery;
import com.barogo.assignment.domain.delivery.DeliveryStatus;
import com.barogo.assignment.infrastructure.persistence.jpa.DeliveryJpaRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

	@Mock DeliveryJpaRepository deliveryRepository;

	@InjectMocks DeliveryService deliveryService;

	FixtureMonkey fieldFixture =
			FixtureMonkey.builder()
					.objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
					.build();

	FixtureMonkey constructorFixture =
			FixtureMonkey.builder()
					.objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
					.build();

	@Test
	void 배송목록_조회() {
		// given
		GetDeliveriesCommand command =
				constructorFixture
						.giveMeBuilder(GetDeliveriesCommand.class)
						.set("id", 100L)
						.set("startDate", LocalDate.of(2024, 1, 1))
						.set("endDate", LocalDate.of(2024, 12, 31))
						.set("pageable", PageRequest.of(0, 10))
						.sample();

		Delivery delivery = fieldFixture.giveMeOne(Delivery.class);
		Page<Delivery> page = new PageImpl<>(List.of(delivery));

		given(
						deliveryRepository.findByCreatedAtBetween(
								eq(command.id()),
								any(LocalDateTime.class),
								any(LocalDateTime.class),
								eq(command.pageable())))
				.willReturn(page);

		// when
		Page<GetDeliveriesResult> result = deliveryService.getDeliveries(command);

		// then
		assertThat(result.getContent().size()).isEqualTo(1);
	}

	@Test
	void 주소변경_성공() {
		// given
		ChangeAddressCommand command =
				constructorFixture
						.giveMeBuilder(ChangeAddressCommand.class)
						.set("id", 1L)
						.set("accountId", 100L)
						.set("address", "서울 강남구")
						.sample();

		Delivery delivery =
				fieldFixture
						.giveMeBuilder(Delivery.class)
						.set("id", 1L)
						.set("status", DeliveryStatus.PENDING)
						.set("address", "서울 마포구")
						.set("account", fieldFixture.giveMeBuilder(Account.class).set("id", 100L).sample())
						.sample();

		given(deliveryRepository.findById(1L)).willReturn(Optional.of(delivery));

		// when
		deliveryService.changeAddress(command);

		// then
		assertThat("서울 강남구").isEqualTo(delivery.getAddress());
	}

	@Test
	void 주소변경_실패_소유자가_아님() {
		// given
		ChangeAddressCommand command =
				constructorFixture
						.giveMeBuilder(ChangeAddressCommand.class)
						.set("id", 1L)
						.set("accountId", 999L)
						.set("address", "서울 성동구")
						.sample();

		Delivery delivery =
				fieldFixture
						.giveMeBuilder(Delivery.class)
						.set("id", 1L)
						.set("status", DeliveryStatus.PENDING)
						.set("address", "서울 마포구")
						.set("account", fieldFixture.giveMeBuilder(Account.class).set("id", 1L).sample())
						.sample();

		given(deliveryRepository.findById(1L)).willReturn(Optional.of(delivery));

		// when & then
		assertThatThrownBy(() -> deliveryService.changeAddress(command))
				.isInstanceOf(NotFoundDeliveryException.class);
	}

	@Test
	void 주소변경_실패_배송상태가변경불가() {
		// given
		ChangeAddressCommand command =
				constructorFixture
						.giveMeBuilder(ChangeAddressCommand.class)
						.set("id", 1L)
						.set("accountId", 1L)
						.set("address", "서울 강서구")
						.sample();

		Delivery delivery =
				fieldFixture
						.giveMeBuilder(Delivery.class)
						.set("id", 1L)
						.set("status", DeliveryStatus.DELIVERED)
						.set("address", "서울 마포구")
						.set("account", fieldFixture.giveMeBuilder(Account.class).set("id", 1L).sample())
						.sample();

		given(deliveryRepository.findById(1L)).willReturn(Optional.of(delivery));

		// when & then
		assertThatThrownBy(() -> deliveryService.changeAddress(command))
				.isInstanceOf(CanNotChangeAddressException.class);
	}
}
