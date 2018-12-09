package com.financetracker.web.rest;

import com.financetracker.entities.*;
import com.financetracker.services.AccountService;
import com.financetracker.services.BudgetService;
import com.financetracker.services.EmployeeService;
import com.financetracker.services.PlannedPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private BudgetService budgetService;

  @RequestMapping(value = "/employees", method = RequestMethod.GET)
  public String getEmployees(Model viewModel) {
    TreeSet<User> employees = employeeService.getAllEmployees();
    viewModel.addAttribute("pagedUsers", employees);
    return "employees";
  }

  @RequestMapping(value = "/employees/transactions/{employeeId}", method = RequestMethod.GET)
  public String getEmployeeTransactions(Model model, @PathVariable("employeeId") Long employeeId) {
    TreeSet<Transaction> transactions = employeeService.getAllTransactionsByUserId(employeeId);
    List<Transaction> transactionsPaged = employeeService.getPagingTransactions(employeeId, 1);
    User employee = employeeService.getEmployeeById(employeeId);

    int pages = (int) Math.ceil(transactions.size() / (double) 10);

    model.addAttribute("employee", employee);
    model.addAttribute("pages", pages);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("transactions", transactions);

    return "employeeTransactions";
  }

  @RequestMapping(value = "/employees/plannedPayments/{employeeId}", method = RequestMethod.GET)
  public String getEmployeePlannedPayments(Model model, @PathVariable("employeeId") Long employeeId) {
    TreeSet<PlannedPayment> plannedPayments = employeeService.getAllPlannedPaymentsByUserId(employeeId);
    List<PlannedPayment> plannedPaymentsPaged = employeeService.getPagingPlannedPayments(employeeId, 1);
    User employee = employeeService.getEmployeeById(employeeId);

    int pages = (int) Math.ceil(plannedPayments.size() / (double) 10);

    model.addAttribute("employee", employee);
    model.addAttribute("plannedPayments", plannedPayments);
    model.addAttribute("pagedPlannedPayments", plannedPaymentsPaged);
    model.addAttribute("pages", pages);

    return "plannedPayments";
  }

  @RequestMapping(value = "/employees/budgets/{employeeId}", method = RequestMethod.GET)
  public String getAllBudgets(Model model, @PathVariable("employeeId") Long employeeId) {
    Map<Budget, BigDecimal> map = budgetService.getBudgets(employeeId);
    model.addAttribute("budgets", map);
    return "budgets";
  }

  @GetMapping(value = "/employees/transactions/{employeeId}/{page}")
  public String transactionPaging(@PathVariable("employeeId") Long employeeId, @PathVariable("page") int page, Model model) {
    List<Transaction> transactionsPaged = employeeService.getPagingTransactions(employeeId, page);
    User employee = employeeService.getEmployeeById(employeeId);

    int allCount = employeeService.getAllTransactionsByUserId(employeeId).size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    model.addAttribute("employee", employee);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("pages", pages);
    return "employeeActivity";
  }
}
