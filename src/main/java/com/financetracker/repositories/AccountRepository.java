package com.financetracker.repositories;

import com.financetracker.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by blagoy
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findByName(String name);

  Account findByAccountId(long accountId);
}
