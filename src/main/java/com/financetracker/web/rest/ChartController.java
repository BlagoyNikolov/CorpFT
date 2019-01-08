package com.financetracker.web.rest;

import com.financetracker.entities.Account;
import com.financetracker.entities.User;
import com.financetracker.services.AccountService;
import com.financetracker.services.ChartService;
import com.financetracker.util.DateConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Controller
public class ChartController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private ChartService chartService;

  @RequestMapping(value = "/cashflowStructure", method = RequestMethod.GET)
  public String getCashFlowStructure(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    Set<Account> accounts = accountService.getAllAccounts();
    Map<String, BigDecimal> transactionCategories = chartService.getCashFlowStructure(user);
    model.addAttribute("accounts", accounts);
    model.addAttribute("transactionsCategories", transactionCategories);

    return "cashflowStructure";
  }

  @RequestMapping(value = "/getTransactions", method = RequestMethod.GET)
  public String getFilteredCashFlowStructure(HttpServletRequest request, HttpSession session, Model model) {
    String date = request.getParameter("date");
    String type = request.getParameter("type");
    String account = request.getParameter("account");

    LocalDateTime[] dateRange = DateConverters.dateRange(date);
    User user = (User) session.getAttribute("user");
    Set<Account> accounts = accountService.getAllAccounts();
    TreeMap<String, BigDecimal> transactions = chartService.getFilteredCashFlowStructure(user, dateRange[0], dateRange[1], type, account);

    model.addAttribute("accounts", accounts);
    model.addAttribute("transactionsCategories", transactions);
    model.addAttribute("date", date);

    return "cashflowStructure";
  }

  @RequestMapping(value = "/incomeVsExpenses", method = RequestMethod.GET)
  public String getIncomeVsExpenses(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    Map<String, BigDecimal> transactions = chartService.getIncomeVsExpenses(user);
    model.addAttribute("transactions", transactions);
    model.addAttribute("accounts", accountService.getAllAccounts());

    return "incomeVsExpenses";
  }

  @RequestMapping(value = "/incomeVsExpenses/filtered", method = RequestMethod.GET)
  public String getFilteredIncomeVsExpenses(HttpSession session, Model model, HttpServletRequest request) {
    User user = (User) session.getAttribute("user");
    String date = request.getParameter("date");
    String account = request.getParameter("account");

    LocalDateTime[] dateRange = DateConverters.dateRange(date);

    long accId = 0;
    if (!account.equals("All Departments")) {
      accId = accountService.getAccountId(user, account);
    }

    Map<String, BigDecimal> transactions = chartService.getFilteredIncomeVsExpenses(user, accId, dateRange[0], dateRange[1]);
    model.addAttribute("transactions", transactions);
    model.addAttribute("date", date);
    model.addAttribute("accounts", accountService.getAllAccounts());

    return "incomeVsExpenses";
  }

  @RequestMapping(value = "/cashflowTrend", method = RequestMethod.GET)
  public String getCashflowTrend(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    Set<Account> accounts = accountService.getAllAccounts();
    BigDecimal allBalance = accountService.calculateAllAccountBalance();
    Map<LocalDate, BigDecimal> finalDefaultTransactions = chartService.getGraphData(user, allBalance);
    model.addAttribute("accounts", accounts);
    model.addAttribute("defaultTransactions", finalDefaultTransactions);

    return "cashflowTrend";
  }

  @RequestMapping(value = "/cashflowTrend/filtered", method = RequestMethod.GET)
  public String getFilteredCashflowTrend(HttpServletRequest request, HttpSession session, Model model) {
    String date = request.getParameter("date");
    String account = request.getParameter("account");
    User user = (User) session.getAttribute("user");
    Set<Account> accounts = accountService.getAllAccounts();
    Map<LocalDate, BigDecimal> finalDefaultTransactions = chartService.getFilteredGraphData(user, date, account, accounts);

    model.addAttribute("accounts", accounts);
    model.addAttribute("defaultTransactions", finalDefaultTransactions);
    model.addAttribute("date", date);

    return "cashflowTrend";
  }
}
