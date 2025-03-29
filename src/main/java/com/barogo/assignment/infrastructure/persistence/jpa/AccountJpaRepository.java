package com.barogo.assignment.infrastructure.persistence.jpa;

import com.barogo.assignment.domain.account.Account;
import com.barogo.assignment.domain.account.AccountRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, Long>, AccountRepository {
	Optional<Account> findByUsername(String username);
}
