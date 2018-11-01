package com.financetracker.services.Impl;

import com.financetracker.entities.*;
import com.financetracker.repositories.PlannedPaymentRepository;
import com.financetracker.services.AccountService;
import com.financetracker.services.CategoryService;
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

  public List<PlannedPayment> getAllPlannedPayments() {
    return plannedPaymentRepository.findAll();
  }

  public List<PlannedPayment> getAllPlannedPaymentsByAccountId(long accountId) {
    return plannedPaymentRepository.findByAccountAccountId(accountId);
  }

  public List<PlannedPayment> getAllPlannedPaymentsByCategoryId(long categoryId) {
    return plannedPaymentRepository.findByCategoryCategoryId(categoryId);
  }

  public void insertPlannedPayment(PlannedPayment plannedPayment) {
    plannedPaymentRepository.save(plannedPayment);
  }

  public void updatePlannedPayment(PlannedPayment plannedPayment) {
    plannedPaymentRepository.save(plannedPayment);
  }

  public void postPlannedPayment(User user, String account, String category, String name, String type, LocalDateTime date, String amount,
                                 PlannedPayment plannedPayment, long plannedPaymentId) {

    Account acc = accountService.getAccountByAccountName(account);
    Category cat = categoryService.getCategoryByCategoryName(category);
    PlannedPayment payment = new PlannedPayment(name, PaymentType.valueOf(type), date,
        BigDecimal.valueOf(Double.valueOf(amount)), plannedPayment.getDescription(), acc, cat);

    if (plannedPaymentId != 0) {
      payment.setPlannedPaymentId(plannedPaymentId);
      updatePlannedPayment(payment);
    } else {
      insertPlannedPayment(payment);
    }
  }

  public void deletePlannedPayment(long plannedPaymentId) {
    plannedPaymentRepository.delete(plannedPaymentId);
  }

  public PlannedPayment getPlannedPaymentByPlannedPaymentId(long plannedPaymentId) {
    return plannedPaymentRepository.findByPlannedPaymentId(plannedPaymentId);
  }

  public List<PlannedPayment> getAllPlannedPaymentsByUser(User user) {
    //        return plannedPaymentRepository.findByAccountUser(user);
    return plannedPaymentRepository.findAll();
  }

  @Scheduled(cron = "0 0 9 * * ?", zone = "Europe/Athens") //Fire at 9:00am every day
  public void plannedPaymentDailyCronJob() {
    LocalDate localDate = LocalDateTime.now().toLocalDate();
    LocalDateTime localDateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0, 0);

    List<PlannedPayment> plannedPaymentsWithCurrentDate = plannedPaymentRepository.findAllByFromDate(localDateTime);
    for (PlannedPayment plannedPayment : plannedPaymentsWithCurrentDate) {
      this.executePlannedPayment(plannedPayment);
    }
  }

  private void executePlannedPayment(PlannedPayment plannedPayment) {
    Account acc = accountService.getAccountByAccountId(plannedPayment.getAccount().getAccountId());
    BigDecimal newValue = plannedPayment.getAmount();
    BigDecimal oldValue = accountService.getAmountByAccountId(acc.getAccountId());
    Transaction transaction = null;

    if (plannedPayment.getPaymentType().equals(PaymentType.EXPENSE)) {
      acc.setAmount(oldValue.subtract(newValue));
      accountService.updateAccount(acc);
      transaction = Transaction.createTransactionByPlannedPayment(PaymentType.EXPENSE, PLANNED_PAYMENT_EXPENSE, plannedPayment, null);
    } else if (plannedPayment.getPaymentType().equals(PaymentType.INCOME)) {
      acc.setAmount(oldValue.add(newValue));
      accountService.updateAccount(acc);
      transaction = Transaction.createTransactionByPlannedPayment(PaymentType.INCOME, PLANNED_PAYMENT_INCOME, plannedPayment, null);
    }
    transactionService.insertTransactionAndBudgetCheck(transaction);
    this.deletePlannedPayment(plannedPayment.getPlannedPaymentId());
  }

  public TreeMap<Integer, List<PlannedPayment>> getPlannedPaymentsChunks(User user) {
    TreeMap<Integer, List<PlannedPayment>> result = new TreeMap<>();
    List<PlannedPayment> plannedPayments = this.getAllPlannedPaymentsByUser(user);
    plannedPayments.sort(new PlannedPaymentComparator());

    List<List<PlannedPayment>> chunks = PagingUtil.chunk(plannedPayments, 10);

    int pageAs = 1;
    for (List<PlannedPayment> pageCountents : chunks) {
      result.put(pageAs++, pageCountents);
    }

    return result;
  }

  @Override
  public List<PlannedPayment> getPagingPlannedPayments(User user, int page) {
    TreeMap<Integer, List<PlannedPayment>> plannedPayments = getPlannedPaymentsChunks(user);
    return plannedPayments.get(page);
  }
}
