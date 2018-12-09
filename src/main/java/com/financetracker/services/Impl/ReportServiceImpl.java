package com.financetracker.services.Impl;

import com.financetracker.entities.Account;
import com.financetracker.entities.Category;
import com.financetracker.entities.PaymentType;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.services.AccountService;
import com.financetracker.services.CategoryService;
import com.financetracker.services.ReportService;
import com.financetracker.util.DateConverters;
import com.financetracker.util.PagingUtil;
import com.financetracker.util.TransactionComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

  public static final String ALL_CATEGORIES = "All categories";
  public static final String ALL_ACCOUNTS = "All accounts";
  public static final String ALL_TYPES = "All types";

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private AccountService accountService;

  public TreeSet<Transaction> getAllReportTransactions(Set<Account> allAccounts) {
    TreeSet<Transaction> allTransactions = new TreeSet<>(new TransactionComparator());
    for (Account acc : accountService.getAllAccounts()) {
      allAccounts.add(acc);
      allTransactions.addAll(acc.getTransactions());
    }

    return allTransactions;
  }

  public TreeSet<Transaction> getFilteredReportTransactions(User user, String categoryName, String type, String accName, String date,
                                                            Set<Account> allAccounts) {
    LocalDateTime[] dateRange = DateConverters.dateRange(date);
    TreeSet<Transaction> transactions = new TreeSet<>(new TransactionComparator());
    transactions.addAll(getReportTransactions(user, type,
        categoryName.equals(ALL_CATEGORIES) ? 0 : categoryService.getCategoryByCategoryName(categoryName).getCategoryId(),
        accName.equals(ALL_ACCOUNTS) ? 0 : accountService.getAccountId(user, accName), dateRange[0], dateRange[1]));

    for (Account acc : accountService.getAllAccounts()) {
      allAccounts.add(acc);
    }
    return transactions;
  }

  public Set<Transaction> getReportTransactions(User user, String type, long categoryId, long accountId,
                                                LocalDateTime from, LocalDateTime to) {
    Set<Transaction> result = new HashSet<>();
    Set<Category> categories = new HashSet<>();

    if (categoryId == 0) {
      if (type.equals(ALL_TYPES)) {
        categories = categoryService.getAllCategories();
      } else {
        categories = categoryService.getAllCategoriesByType(PaymentType.valueOf(type));
      }
    } else {
      categories.add(categoryService.getCategoryByCategoryId(categoryId));
    }

    if (accountId == 0) {
      categories.stream().filter(cat -> cat.getTransactions().size() != 0).forEach(cat -> {
        Set<Transaction> transactions = cat.getTransactions()
            .stream()
            .filter(transaction -> (transaction.getDate().isAfter(from) && transaction.getDate().isBefore(to)))
            .collect(Collectors.toSet());
        result.addAll(transactions);
      });
    } else {
      categories.stream().filter(cat -> cat.getTransactions().size() != 0).forEach(cat -> {
        Set<Transaction> transactions = cat.getTransactions()
            .stream()
            .filter(transaction -> (transaction.getDate().isAfter(from) && transaction.getDate().isBefore(to)))
            .filter(transaction -> (transaction.getAccount().getName().equals(accountService.getAccountNameByAccountId(accountId))))
            .collect(Collectors.toSet());
        result.addAll(transactions);
      });
    }
    return result;
  }

  public List<Transaction> getPagingTransactions(TreeSet<Transaction> allTransactions, int page) {
    TreeMap<Integer, List<Transaction>> transactions = getTransactionChunks(allTransactions);
    return transactions.get(page);
  }

  private TreeMap<Integer, List<Transaction>> getTransactionChunks(TreeSet<Transaction> allTransactions) {
    TreeMap<Integer, List<Transaction>> result = new TreeMap<>();
    List<Transaction> transactions = new ArrayList<>();
    transactions.addAll(allTransactions);
    transactions.sort(new TransactionComparator());

    List<List<Transaction>> chunks = PagingUtil.chunk(transactions, 10);

    int pageAs = 1;
    for (List<Transaction> pageCountents : chunks) {
      result.put(pageAs++, pageCountents);
    }

    return result;
  }
}
