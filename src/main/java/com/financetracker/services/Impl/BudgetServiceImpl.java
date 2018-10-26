package com.financetracker.services.Impl;

import com.financetracker.model.Account;
import com.financetracker.model.Budget;
import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;
import com.financetracker.repositories.BudgetRepository;
import com.financetracker.services.BudgetService;
import com.financetracker.services.CategoryService;
import com.financetracker.services.TransactionService;
import com.financetracker.util.DateConverters;
import com.financetracker.util.PagingUtil;
import com.financetracker.util.TransactionComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    public List<Budget> getAllBudgetsByAccount(Account account) {
        return budgetRepository.findByAccount(account);
    }

    public List<Budget> getAllBudgetsByCategory(Category category) {
        return budgetRepository.findByCategory(category);
    }

    public void insertBudget(Budget budget) {
        boolean exits = transactionService.existsTransaction(budget);
        if (exits) {
            Set<Transaction> transactions = transactionService.getAllTransactionsForBudget(budget);

            BigDecimal amount = transactions
                .stream()
                .map(tr -> tr.getAmount())
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

            budget.setAmount(amount);
            budget.setTransactions(transactions);
            budgetRepository.save(budget);
            return;
        }
        budgetRepository.save(budget);
    }

    public void updateBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    public void postBudget(Budget budget, User user, Account account, Category category, String date) {
        String[] inputDate = date.split("/");
        int monthFrom = Integer.valueOf(inputDate[0]);
        int dayOfMonthFrom = Integer.valueOf(inputDate[1]);

        String[] temp = inputDate[2].split(" - ");
        int yearFrom = Integer.valueOf(temp[0]);
        int monthTo = Integer.valueOf(temp[1]);
        int dayOfMonthTo = Integer.valueOf(inputDate[3]);
        int yearTo = Integer.valueOf(inputDate[4]);

        LocalDateTime dateFrom = LocalDateTime.of(yearFrom, monthFrom, dayOfMonthFrom, 0, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(yearTo, monthTo, dayOfMonthTo, 0, 0, 0);

        budget.setFromDate(dateFrom);
        budget.setToDate(dateTo);
        budget.setAccount(account);
        budget.setCategory(category);

        insertBudget(budget);
    }

    public void postEditBudget(Long budgetId, Budget budget, User user, Budget oldBudget, Account acc,
                               Category category, String date) {
        LocalDateTime[] dateRange = DateConverters.dateRange(date);

        Budget newBudget = new Budget(budget.getName(), budget.getInitialAmount(),
                dateRange[0], dateRange[1], acc, category);
        newBudget.setBudgetId(budgetId);

        boolean exist = newBudget.getCategory().getCategoryId() != oldBudget.getCategory().getCategoryId()
                || newBudget.getAccount().getAccountId() != oldBudget.getAccount().getAccountId()
                || newBudget.getFromDate() != oldBudget.getFromDate()
                || newBudget.getToDate() != oldBudget.getToDate();

        if (exist) {
            transferTransactions(newBudget);
        }
    }

    public void transferTransactions(Budget newBudget) {
        Set<Transaction> transactions = newBudget.getTransactions();
        BigDecimal newAmount = new BigDecimal(0.0);

        for (Transaction transaction : transactions) {
            newAmount = newAmount.subtract(transaction.getAmount());
        }
        newBudget.setAmount(newAmount);
        newBudget.removeTransactions();

        boolean exits = transactionService.existsTransaction(newBudget);
        if (exits) {
            transactions = transactionService.getAllTransactionsForBudget(newBudget);
            newAmount = new BigDecimal(0.0);

            for (Transaction transaction : transactions) {
                newAmount = newAmount.add(transaction.getAmount());
            }
            newBudget.setAmount(newAmount);
            newBudget.setTransactions(transactions);
            updateBudget(newBudget);
        }
    }

    public void deleteBudget(Budget budget) {
        budgetRepository.delete(budget);
    }

    public boolean existsBudget(LocalDateTime date, Category category, Account account) {
        Set<Budget> budgets = budgetRepository.findByCategoryAndAccount(category, account);
        for (Budget budget : budgets) {
            if (isBetweenTwoDates(date, budget.getFromDate(), budget.getToDate())) {
                return true;
            }
        }
        return false;
    }

    public Map<Budget, BigDecimal> getBudgets(User user) {
        Set<Budget> budgets = null;
        BigDecimal percent = new BigDecimal(0.0);

        Map<Budget, BigDecimal> map = new TreeMap<>((b1, b2) -> {
            if (b2.getFromDate().compareTo(b1.getFromDate()) == 0) {
                return Long.compare(b2.getBudgetId(), b1.getBudgetId());
            }
            return b2.getFromDate().compareTo(b1.getFromDate());
        });

        budgets = getAllBudgetsByUser(user);
        for (Budget budget : budgets) {
            percent = budget.getAmount().divide(budget.getInitialAmount(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            map.put(budget, percent);
        }
        return map;
    }

    public TreeSet<Transaction> getBudgetTransactions(Long budgetId) {
        Budget budget = getBudgetByBudgetId(budgetId);
        TreeSet<Transaction> transactions = new TreeSet<>((t1, t2) -> {
            if (t2.getDate().compareTo(t1.getDate()) == 0) {
                return Long.compare(t2.getTransactionId(), t1.getTransactionId());
            }
            return t2.getDate().compareTo(t1.getDate());
        });

        for (Transaction transaction : budget.getTransactions()) {
            transaction.setCategoryName(categoryService.getCategoryNameByCategoryId(transaction.getCategory().getCategoryId()));
            transactions.add(transaction);
        }
        return transactions;
    }

    public Set<Budget> getAllBudgetsByDateCategoryAndAccount(LocalDateTime date, Category category, Account account) {
        Set<Budget> result = new HashSet<Budget>();
        Set<Budget> budgets = budgetRepository.findByCategoryAndAccount(category, account);
        for (Budget budget : budgets) {
            if (isBetweenTwoDates(date, budget.getFromDate(), budget.getToDate())) {
                result.add(budget);
            }
        }
        return result;
    }

    public boolean isBetweenTwoDates(LocalDateTime date, LocalDateTime from, LocalDateTime to) {
        return !date.isBefore(from) && !date.isAfter(to);
    }

    public Set<Budget> getAllBudgetsByUser(User user) {
//        return budgetRepository.findByAccountUser(user);
        List<Budget> budgetList = budgetRepository.findAll();
        Set<Budget> budgetSet = new HashSet<>();
        budgetSet.addAll(budgetList);
        return budgetSet;
    }

    public Budget getBudgetByBudgetId(long budgetId) {
        return budgetRepository.findByBudgetId(budgetId);
    }

    private TreeMap<Integer,List<Transaction>> getAccountTransactionChunks(Long budgetId, int page) {
        TreeMap<Integer, List<Transaction>> result = new TreeMap<>();
        TreeSet<Transaction> transactions = this.getBudgetTransactions(budgetId);
        List<Transaction> transactionsList = new ArrayList<>();
        transactionsList.addAll(transactions);
        transactionsList.sort(new TransactionComparator());

        List<List<Transaction>> chunks = PagingUtil.chunk(transactionsList, 10);

        int pageAs = 1;
        for (List<Transaction> pageCountents : chunks) {
            result.put(pageAs++, pageCountents);
        }

        return result;
    }

    @Override
    public List<Transaction> getPagingTransactions(Long budgetId, int page) {
        TreeMap<Integer, List<Transaction>> transactions = getAccountTransactionChunks(budgetId, page);
        return  transactions.get(page);
    }


}
