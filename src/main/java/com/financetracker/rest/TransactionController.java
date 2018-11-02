package com.financetracker.rest;

import com.financetracker.entities.Account;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.services.AccountService;
import com.financetracker.services.CategoryService;
import com.financetracker.services.CurrencyService;
import com.financetracker.services.TransactionService;
import com.financetracker.util.DateConverters;
import com.financetracker.util.TransactionComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(value = "/account")
public class TransactionController {
  public static final String COULD_NOT_MAKE_TRANSFER_MESSAGE = "Could not make transfer. Please, enter valid data!";
  public static final String COULD_NOT_UPDATE_TRANSACTION_MESSAGE = "Could not update transaction. Please, enter valid data!";
  public static final String COULD_NOT_INSERT_TRANSACTION_MESSAGE = "Could not insert transaction. Please, enter valid data!";

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private CurrencyService currencyService;

  @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
  public String getAllTransactions(@PathVariable("accountId") Long accountId, Model model, HttpSession session) {
    TreeSet<Transaction> transactions = new TreeSet<>(new TransactionComparator());
    transactions.addAll(transactionService.getAllTransactionsByAccountId(accountId));
    String accountName = accountService.getAccountNameByAccountId(accountId);
    String balance = accountService.setAccountBalance(accountId);
    List<Transaction> transactionsPaged = transactionService.getPagingTransactions(accountId, 1);
    User user = (User) session.getAttribute("user");

    int allCount = transactionService.getAllTransactionsByAccountId(accountId).size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    model.addAttribute("pages", pages);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("accountId", accountId);
    session.setAttribute("user", user);
    session.setAttribute("accountName", accountName);
    session.setAttribute("balance", balance);
    session.setAttribute("transactions", transactions);

    return "transactions";
  }

  @RequestMapping(value = "/addTransaction", method = RequestMethod.GET)
  public String getAddTransaction(HttpSession session, Model model) {
    Transaction transaction = new Transaction();
    session.setAttribute("link", "account/addTransaction");
    model.addAttribute("currencies", currencyService.getCurrencyList());
    model.addAttribute("transaction", transaction);
    return "addTransaction";
  }

  @RequestMapping(value = "/addTransaction", method = RequestMethod.POST)
  public String postAddTransaction(HttpServletRequest request, Model model,
                                   @Valid @ModelAttribute("transaction") Transaction transaction, BindingResult bindingResult) {
    String type = request.getParameter("type");
    String account = request.getParameter("account");
    String category = request.getParameter("category");
    String amount = request.getParameter("amount");
    String currency = request.getParameter("currency");

    if (type.isEmpty() || account.isEmpty() || category.isEmpty() || bindingResult.hasErrors()) {
      model.addAttribute("error", COULD_NOT_INSERT_TRANSACTION_MESSAGE);
      Transaction defaultTransaction = new Transaction();
      return "addTransaction";
    }

    User user = (User) request.getSession().getAttribute("user");
    transactionService.postTransaction(user, account, category, type, LocalDateTime.now(), amount, transaction, 0, currency);
    Account acc = accountService.getAccountByAccountName(account);

    return "redirect:/account/" + acc.getAccountId();
  }

  @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
  public String getEditTransaction(HttpSession session, Model model, @PathVariable("transactionId") Long transactionId) {
    Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);
    String type = transaction.getType().toString();
    String description = transaction.getDescription();
    BigDecimal amount = transaction.getAmount();
    String accountName = accountService.getAccountNameByAccountId(transaction.getAccount().getAccountId());
    String category = categoryService.getCategoryNameByCategoryId(transaction.getCategory().getCategoryId());
    LocalDateTime date = transaction.getDate();
    User user = (User) session.getAttribute("user");

    model.addAttribute("transaction", transaction);
    model.addAttribute("editTransactionType", type);
    model.addAttribute("editTransactionDescription", description);
    model.addAttribute("editTransactionAmount", amount);
    model.addAttribute("editTransactionAccount", accountName);
    model.addAttribute("editTransactionCategory", category);
    model.addAttribute("editTransactionDate", date);

    session.setAttribute("user", user);
    session.setAttribute("link", "account/transaction/" + transactionId);
    session.setAttribute("transactionId", transactionId);

    return "editTransaction";
  }

  @RequestMapping(value = "/transaction/editTransaction", method = RequestMethod.POST)
  public String postEditTransaction(HttpServletRequest request, HttpSession session, Model model,
                                    @Valid @ModelAttribute("transaction") Transaction transaction, BindingResult bindingResult) {
    String type = request.getParameter("type");
    String account = request.getParameter("account");
    String category = request.getParameter("category");
    String amount = request.getParameter("amount");
    String date = request.getParameter("date");
    String currency = request.getParameter("currency");
    long transactionId = (long) request.getSession().getAttribute("transactionId");

    if (type.isEmpty() || account.isEmpty() || category.isEmpty() || bindingResult.hasErrors()) {
      model.addAttribute("error", COULD_NOT_UPDATE_TRANSACTION_MESSAGE);
      Transaction defaultTransaction = new Transaction();

      LocalDateTime newDate = DateConverters.convertFromStringToLocalDateTime(date);
      model.addAttribute("editTransactionType", type);
      model.addAttribute("editTransactionDescription", transaction.getDescription());
      model.addAttribute("editTransactionAmount", amount);
      model.addAttribute("editTransactionAccount", account);
      model.addAttribute("editTransactionCategory", category);
      model.addAttribute("editTransactionDate", newDate);

      return "editTransaction";
    }

    LocalDateTime newDate = DateConverters.convertFromStringToLocalDateTime(date);
    User user = (User) session.getAttribute("user");
    transactionService.postTransaction(user, account, category, type, newDate, amount, transaction, transactionId, currency);
    Account acc = accountService.getAccountByAccountName(account);

    return "redirect:/account/" + acc.getAccountId();
  }

  @RequestMapping(value = "/transfer/accountId/{accountId}", method = RequestMethod.GET)
  public String getTransfer(Model model, @PathVariable("accountId") Long originAccountId) {
    Account originAccount = accountService.getAccountByAccountId(originAccountId);
    Set<Account> userAccounts = accountService.getAllAccounts();
    model.addAttribute("firstAccount", originAccount);
    model.addAttribute("userAccounts", userAccounts);

    return "transfer";
  }

  @RequestMapping(value = "/transfer/accountId/transfer", method = RequestMethod.POST)
  public String postTransfer(HttpServletRequest request, HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    String amountParam = request.getParameter("amount");
    String fromAccount = request.getParameter("fromAccount");
    String toAccount = request.getParameter("toAccount");

    if (amountParam.isEmpty() || fromAccount.equals(toAccount)
        || BigDecimal.valueOf(Double.valueOf(amountParam)).compareTo(BigDecimal.ZERO) < 0
        || BigDecimal.valueOf(Double.valueOf(amountParam)).compareTo(BigDecimal.ZERO) == 0) {
      model.addAttribute("error", COULD_NOT_MAKE_TRANSFER_MESSAGE);

      Account originAccount = accountService.getAccountByAccountName(fromAccount);
      Set<Account> userAccounts = accountService.getAllAccounts();

      model.addAttribute("firstAccount", originAccount);
      model.addAttribute("userAccounts", userAccounts);

      return "transfer";
    }

    Account from = accountService.getAccountByAccountName(fromAccount);
    accountService.makeTransferToOtherAccount(user, fromAccount, toAccount, amountParam);

    return "redirect:/account/" + from.getAccountId();
  }

  @RequestMapping(value = "transaction/deleteTransaction/{transactionId}", method = RequestMethod.POST)
  public String deleteTransaction(@PathVariable("transactionId") Long transactionId, HttpSession session) {
    User user = (User) session.getAttribute("user");
    Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);
    transactionService.deleteTransaction(user, transactionId);
    return "redirect:/account/" + transaction.getAccount().getAccountId();
  }

  @GetMapping(value = "/{accountId}/{page}")
  public String transactionPaging(@PathVariable("accountId") Long accountId, @PathVariable("page") int page, Model model) {
    List<Transaction> transactionsPaged = transactionService.getPagingTransactions(accountId, page);
    int allCount = transactionService.getAllTransactionsByAccountId(accountId).size();
    int pages = (int) Math.ceil(allCount / (double) 10);
    String accountName = accountService.getAccountNameByAccountId(accountId);
    BigDecimal accountBalance = accountService.getAmountByAccountId(accountId);
    String balance = NumberFormat.getCurrencyInstance(Locale.US).format(accountBalance);
    //        Set<Account> accounts = accountService.getAllAccounts();

    //        entities.addAttribute("accounts", accounts);
    model.addAttribute("accountName", accountName);
    model.addAttribute("balance", balance);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("accountId", accountId);
    model.addAttribute("pages", pages);
    return "transactions";
  }
}
