package com.barogo.assignment.domain.delivery;

import com.barogo.assignment.domain.account.Account;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DeliveryTest {

	FixtureMonkey fixture =
			FixtureMonkey.builder()
					.objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
					.build();

	@Test
	void 배송상태가_PENDING_이_아니면_주소변경불가() {
		// given
		Delivery delivery =
				fixture.giveMeBuilder(Delivery.class).set("status", DeliveryStatus.DELIVERED).sample();

		// when
		boolean result = delivery.canNotChangeAddress();

		// then
		Assertions.assertThat(result).isTrue();
	}

	@Test
	void 배송상태가_PENDING이면_주소변경가능() {
		// given
		Delivery delivery =
				fixture.giveMeBuilder(Delivery.class).set("status", DeliveryStatus.PENDING).sample();
		// when
		boolean result = delivery.canNotChangeAddress();

		// then
		Assertions.assertThat(result).isFalse();
	}

	@Test
	void 계정ID가_다르면_소유자가_아님() {
		// given
		Account account = fixture.giveMeBuilder(Account.class).set("id", 1L).sample();

		Delivery delivery =
				fixture
						.giveMeBuilder(Delivery.class)
						.set("status", DeliveryStatus.DELIVERED)
						.set("account", account)
						.sample();

		// when
		boolean result = delivery.isNotOwner(2L);

		// then
		Assertions.assertThat(result).isTrue();
	}
}
