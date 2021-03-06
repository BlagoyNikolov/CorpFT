package com.financetracker.services.Impl;

import com.financetracker.entities.*;
import com.financetracker.repositories.PlannedPaymentRepository;
import com.financetracker.services.AccountService;
import com.financetracker.services.CategoryService;
import com.financetracker.services.CurrencyService;
import com.financetracker.services.PlannedPaymentService;
import com.financetracker.services.TransactionService;
import com.financetracker.util.PagingUtil;
import com.financetracker.util.PlannedPaymentComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@Service
public class PlannedPaymentServiceImpl implements PlannedPaymentService {

  public static final String PLANNED_PAYMENT_EXPENSE = "Planned Payment Expense";
  public static final String PLANNED_PAYMENT_INCOME = "Planned Payment Income";

  @Autowired
  private PlannedPaymentRepository plannedPaymentRepository;

  @Autowired
  private AccountService accountService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private CurrencyService currencyService;

  public void addPlannedPayment(User user, String account, String category, String name, String type, LocalDateTime date, String amount,
                                String currencyId, PlannedPayment plannedPayment) {

    PlannedPayment payment = createPlannedPayment(user, account, category, name, type, date, amount, currencyId, plannedPayment);
    plannedPaymentRepository.save(payment);
  }

  public void editPlannedPayment(User user, String account, String category, String name, String type, LocalDateTime date, String amount,
                                 String currencyId, PlannedPayment plannedPayment, Long plannedPaymentId) {

    PlannedPayment payment = createPlannedPayment(user, account, category, name, type, date, amount, currencyId, plannedPayment);
    payment.setPlannedPaymentId(plannedPaymentId);
    plannedPaymentRepository.save(payment);
  }

  private PlannedPayment createPlannedPayment(User user, String account, String category, String name, String type, LocalDateTime date,
                                              String amount, String currencyId, PlannedPayment plannedPayment) {

    Account acc = accountService.getAccountByAccountName(account);
    Category cat = categoryService.getCategoryByCategoryName(category);
    Currency currency = currencyService.getCurrencyByCurrencyName(currencyId);
    BigDecimal accountAmount = currencyService.convertToAccountCurrency(currency, acc.getCurrency(), plannedPayment.getAmount());
    BigDecimal eurAmount = currencyService.convertToEuro(currency, plannedPayment.getAmount());
    return new PlannedPayment.PlannedPaymentBuilder()
        .setName(name)
        .setPaymentType(PaymentType.valueOf(type))
        .setDate(date)
        .setAmount(BigDecimal.valueOf(Double.valueOf(amount)))
        .setAccountAmount(accountAmount)
        .setEurAmount(eurAmount)
        .setDescription(plannedPayment.getDescription())
        .setAccount(acc)
        .setCategory(cat)
        .setUser(user)
        .setCurrency(currency)
        .setAccountCurrency(acc.getCurrency())
        .setInsertedBy(user.getFirstName() + " " + user.getLastName())
        .setCategoryName(cat.getName())
        .build();
  }

  public PlannedPayment getPlannedPaymentByPlannedPaymentId(long plannedPaymentId) {
    return plannedPaymentRepository.findByPlannedPaymentId(plannedPaymentId);
  }

  public List<PlannedPayment> getAllPlannedPayments() {
    return plannedPaymentRepository.findAll();
  }

  @Scheduled(cron = "0 0 9 * * ?", zone = "Europe/Athens") //Fire at 9:00am every day
  private void plannedPaymentDailyCronJob() {
    LocalDate localDate = LocalDateTime.now().toLocalDate();
    LocalDateTime localDateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0, 0);

    List<PlannedPayment> plannedPaymentsWithCurrentDate = plannedPaymentRepository.findAllByFromDate(localDateTime);
    for (PlannedPayment plannedPayment : plannedPaymentsWithCurrentDate) {
      this.executePlannedPayment(plannedPayment);
    }
  }

  private void executePlannedPayment(PlannedPayment plannedPayment) {
    Account acc = accountService.getAccountByAccountId(plannedPayment.getAccount().getAccountId());
    BigDecimal newValue = plannedPayment.getAccountAmount();
    BigDecimal oldValue = accountService.getAmountByAccountId(acc.getAccountId());
    Transaction transaction = null;

    if (plannedPayment.getPaymentType().equals(PaymentType.EXPENSE)) {
      acc.setAmount(oldValue.subtract(newValue));
      accountService.updateAccount(acc);
      transaction = createTransactionByPlannedPayment(PaymentType.EXPENSE, PLANNED_PAYMENT_EXPENSE, plannedPayment, null, plannedPayment.getAmount());
    } else if (plannedPayment.getPaymentType().equals(PaymentType.INCOME)) {
      acc.setAmount(oldValue.add(newValue));
      accountService.updateAccount(acc);
      transaction = createTransactionByPlannedPayment(PaymentType.INCOME, PLANNED_PAYMENT_INCOME, plannedPayment, null, plannedPayment.getAmount());
    }
    transactionService.insertTransaction(transaction);
    deletePlannedPayment(plannedPayment.getPlannedPaymentId());
  }

  public void deletePlannedPayment(long plannedPaymentId) {
    plannedPaymentRepository.delete(plannedPaymentId);
  }

  @Override
  public List<PlannedPayment> getPagingPlannedPayments(int page) {
    TreeMap<Integer, List<PlannedPayment>> plannedPayments = getPlannedPaymentsChunks();
    return plannedPayments.get(page);
  }

  private TreeMap<Integer, List<PlannedPayment>> getPlannedPaymentsChunks() {
    TreeMap<Integer, List<PlannedPayment>> result = new TreeMap<>();
    List<PlannedPayment> plannedPayments = this.getAllPlannedPayments();
    plannedPayments.sort(new PlannedPaymentComparator());

    List<List<PlannedPayment>> chunks = PagingUtil.chunk(plannedPayments, 10);

    int pageAs = 1;
    for (List<PlannedPayment> pageCountents : chunks) {
      result.put(pageAs++, pageCountents);
    }

    return result;
  }

  private Transaction createTransactionByPlannedPayment(PaymentType type, String description, PlannedPayment plannedPayment, User user, BigDecimal accountAmount) {
    return new Transaction.TransactionBuilder()
        .setPaymentType(type)
        .setDescription(description)
        .setAmount(plannedPayment.getAmount())
        .setAccount(plannedPayment.getAccount())
        .setEurAmount(plannedPayment.getEurAmount())
        .setCategory(plannedPayment.getCategory())
        .setCategoryName(plannedPayment.getAccount().getName())
        .setDate(LocalDateTime.now())
        .setUser(user)
        .setInsertedBy("Inserted by system")
        .setCurrency(plannedPayment.getCurrency())
        .setAccountCurrency(plannedPayment.getAccount().getCurrency())
        .setAccountAmount(accountAmount)
        .build();
  }
}
