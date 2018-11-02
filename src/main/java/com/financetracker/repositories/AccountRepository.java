package com.financetracker.repositories;

import com.financetracker.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by blagoy
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

  //    Account findByUserAndName(User user, String name);
  Account findByName(String name);

  //    Set<Account> findByUser(User user);

  Account findByAccountId(long accountId);
}
