package com.financetracker.rest;

import com.financetracker.entities.Account;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class ReportController {

  @Autowired
  private ReportService reportService;

  @RequestMapping(value = "/reports", method = RequestMethod.GET)
  public String getAllReports(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    Set<Account> allAccounts = new TreeSet<>((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
    TreeSet<Transaction> allTransactions = reportService.getAllReportTransactions(user, allAccounts);

    List<Transaction> transactionsPaged = reportService.getPagingTransactions(allTransactions, 1);
    int allCount = allTransactions.size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    model.addAttribute("allAccounts", allAccounts);
    model.addAttribute("allTransactions", allTransactions);
    model.addAttribute("pages", pages);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("filtered", false);

    return "reports";
  }

  @RequestMapping(value = "/reports/filtered", method = RequestMethod.GET)
  public String filterTransactions(HttpServletRequest request, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    String categoryName = request.getParameter("category");
    String type = request.getParameter("type");
    String accName = request.getParameter("account");
    String date = request.getParameter("date");
    Set<Account> allAccounts = new TreeSet<>((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
    TreeSet<Transaction> transactions = reportService.getFilteredReportTransactions(user, categoryName, type, accName, date, allAccounts);

    List<Transaction> transactionsPaged = reportService.getPagingTransactions(transactions, 1);
    int allCount = transactions.size();
    int pages = (int) Math.ceil(allCount / (double) 10);


    model.addAttribute("date", date);
    model.addAttribute("type", type);
    model.addAttribute("category", categoryName);
    model.addAttribute("account", accName);
    model.addAttribute("allAccounts", allAccounts);
    model.addAttribute("allTransactions", transactions);
    model.addAttribute("pages", pages);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("filtered", true);

    return "reports";
  }

  @GetMapping(value = "/reports/{page}")
  public String transactionPaging(@PathVariable("page") int page, HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    Set<Account> allAccounts = new TreeSet<>((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
    TreeSet<Transaction> allTransactions = reportService.getAllReportTransactions(user, allAccounts);

    List<Transaction> transactionsPaged = reportService.getPagingTransactions(allTransactions, page);
    int allCount = allTransactions.size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    model.addAttribute("allAccounts", allAccounts);
    model.addAttribute("allTransactions", allTransactions);
    model.addAttribute("pages", pages);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("filtered", false);

    return "reports";
  }

  @RequestMapping(value = "/reports/filtered/{page}", method = RequestMethod.GET)
  public String filterTransactionsByPage(@PathVariable("page") int page, HttpServletRequest request, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");

    String date = request.getParameter("dateFiler");
    String type = request.getParameter("typeFiler");
    String categoryName = request.getParameter("categoryFiler");
    String accName = request.getParameter("accountFiler");

    Set<Account> allAccounts = new TreeSet<>((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
    TreeSet<Transaction> transactions = reportService.getFilteredReportTransactions(user, categoryName, type, accName, date, allAccounts);

    List<Transaction> transactionsPaged = reportService.getPagingTransactions(transactions, page);
    int allCount = transactions.size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    model.addAttribute("date", date);
    model.addAttribute("type", type);
    model.addAttribute("category", categoryName);
    model.addAttribute("account", accName);
    model.addAttribute("allAccounts", allAccounts);
    model.addAttribute("allTransactions", transactions);
    model.addAttribute("pages", pages);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("filtered", true);

    return "reports";
  }
}
