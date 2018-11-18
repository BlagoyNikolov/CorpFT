package com.financetracker.services;

import com.financetracker.entities.Account;
import com.financetracker.entities.User;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

/**
 * Created by blagoy
 */
public interface AccountService {

  void insertAccount(Account account, User user);

  void deleteAccount(long accountId, User user);

  long getAccountId(User user, String name);

  //    Set<Account> getAllAccountsByUser(User user);

  Set<Account> getAllAccounts();

  Account getAccountByAccountId(long accountId);

  void updateAccount(Account account);

  void makeTransferToOtherAccount(User user, String inputFromAccount, String inputToAccount, String inputAmount);

  BigDecimal getAmountByAccountId(long accountId);

  String getAccountNameByAccountId(long accountId);

  Account getAccountByAccountName(String name);

  Optional<Account> getExistingAccount(String name);

  String setAccountBalance(Long accountId);

  BigDecimal calculateAllAccountBalance();
}
