package com.barogo.assignment.domain.delivery;

import com.barogo.assignment.domain.BaseEntity;
import com.barogo.assignment.domain.account.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Delivery extends BaseEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private String address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	public void changeAddress(String address) {
		this.address = address;
	}

	public boolean isNotOwner(long accountId) {
		return this.account == null || this.account.getId() != accountId;
	}

	public boolean canNotChangeAddress() {
		return this.status != DeliveryStatus.PENDING;
	}
}
