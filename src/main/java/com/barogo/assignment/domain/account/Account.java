package com.barogo.assignment.domain.account;

import com.barogo.assignment.domain.BaseEntity;
import com.barogo.assignment.domain.delivery.Delivery;
import jakarta.persistence.*;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account extends BaseEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private String username;
	private String password;
	private String name;

	@OneToMany(mappedBy = "account")
	private List<Delivery> deliveries;

	private Account(String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public static Account create(String username, String password, String name) {
		return new Account(username, password, name);
	}
}
