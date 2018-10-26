package com.financetracker.repositories;

import com.financetracker.model.Account;
import com.financetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by blagoy
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    //    Account findByUserAndName(User user, String name);
    Account findByName(String name);

//    Set<Account> findByUser(User user);

    Account findByAccountId(long accountId);
}
