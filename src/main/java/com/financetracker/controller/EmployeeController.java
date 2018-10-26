package com.financetracker.controller;

import com.financetracker.model.Account;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;
import com.financetracker.services.EmployeeService;
import com.financetracker.util.TransactionComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @RequestMapping(value = "/employees", method = RequestMethod.GET)
  public String getEmployees(Model viewModel) {
    TreeSet<User> employees = employeeService.getAllEmployees();
    viewModel.addAttribute("pagedUsers", employees);
    return "employees";
  }

  @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
  public String getEmployeeActivity(Model viewModel, @PathVariable("employeeId") Long employeeId) {
    TreeSet<Transaction> transactions = employeeService.getAllTransactionsByUserId(employeeId);
    List<Transaction> transactionsPaged = employeeService.getPagingTransactions(employeeId, 1);
    User employee = employeeService.getEmployeeById(employeeId);

    int allCount = employeeService.getAllTransactionsByUserId(employeeId).size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    viewModel.addAttribute("employee", employee);
    viewModel.addAttribute("pages", pages);
    viewModel.addAttribute("pagedTransactions", transactionsPaged);
    viewModel.addAttribute("transactions", transactions);

    return "employeeActivity";
  }

  @GetMapping(value = "/employees/{employeeId}/{page}")
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
