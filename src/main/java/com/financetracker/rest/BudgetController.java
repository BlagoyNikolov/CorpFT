package com.financetracker.rest;

import com.financetracker.entities.Account;
import com.financetracker.entities.Budget;
import com.financetracker.entities.Category;
import com.financetracker.entities.PaymentType;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.services.AccountService;
import com.financetracker.services.BudgetService;
import com.financetracker.services.CategoryService;
import com.financetracker.util.DateConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class BudgetController {
  public static final String USER = "user";
  public static final String ACCOUNT = "account";
  public static final String ACCOUNTS = "accounts";
  public static final String ACCOUNT_NAME = "accountName";
  public static final String BUDGET = "budget";
  public static final String BUDGETS = "budgets";
  public static final String CATEGORY = "category";
  public static final String CATEGORIES = "categories";
  public static final String CATEGORY_NAME = "categoryName";
  public static final String DATE = "date";
  public static final String EDIT_BUDGET_AMOUNT = "editBudgetAmount";
  public static final String INITIAL_AMOUNT = "initialAmount";
  public static final String NAME = "name";
  public static final String BUDGET_ID = "budgetId";

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private BudgetService budgetService;

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "/budgets", method = RequestMethod.GET)
  public String getAllBudgets(HttpSession session, Model model) {
    Map<Budget, BigDecimal> map = budgetService.getBudgets();
    model.addAttribute(BUDGETS, map);
    return "budgets";
  }

  @RequestMapping(value = "/addBudget", method = RequestMethod.GET)
  public String getAddBudget(HttpSession session, Model model) {
    Set<Account> accounts = accountService.getAllAccounts();
    Set<Category> categories = categoryService.getAllCategoriesByType(PaymentType.EXPENSE);

    model.addAttribute(ACCOUNTS, accounts);
    model.addAttribute(CATEGORIES, categories);
    model.addAttribute(BUDGET, new Budget());

    return "addBudget";
  }

  @RequestMapping(value = "/addBudget", method = RequestMethod.POST)
  public String postAddBudget(HttpServletRequest request, HttpSession session, Model model,
                              @Valid @ModelAttribute("budget") Budget budget, BindingResult bindingResult) {
    User user = (User) session.getAttribute(USER);

    budget.setAmount(BigDecimal.valueOf(0));
    String amountString = request.getParameter(INITIAL_AMOUNT);
    BigDecimal amount = BigDecimal.valueOf(Long.valueOf(amountString));
    budget.setInitialAmount(amount);

    Account account = accountService.getAccountByAccountName(request.getParameter(ACCOUNT));
    Category category = categoryService.getCategoryByCategoryName(request.getParameter(CATEGORY));
    String date = request.getParameter(DATE);

    budgetService.postBudget(budget, user, account, category, date);
    session.setAttribute("link", "addBudget");

    return "redirect:/budgets";
  }

  @RequestMapping(value = "/budgets/{budgetId}/editBudget", method = RequestMethod.GET)
  public String getEditBudget(HttpSession session, @PathVariable("budgetId") Long budgetId, Model model) {
    Budget budget = budgetService.getBudgetByBudgetId(budgetId);

    String date = DateConverters.convertBudgetDate(budget);

    Account acc = accountService.getAccountByAccountId(budget.getAccount().getAccountId());
    Set<Account> accounts = accountService.getAllAccounts();
    BigDecimal amount = budget.getInitialAmount();
    String categoryName = categoryService.getCategoryNameByCategoryId(budget.getCategory().getCategoryId());
    Set<Category> categories = categoryService.getAllCategoriesByType(PaymentType.EXPENSE);
    User user = (User) session.getAttribute(USER);

    model.addAttribute(DATE, date);
    model.addAttribute(ACCOUNT_NAME, acc.getName());
    model.addAttribute(ACCOUNTS, accounts);
    model.addAttribute(EDIT_BUDGET_AMOUNT, amount);
    model.addAttribute(CATEGORY_NAME, categoryName);
    model.addAttribute(CATEGORIES, categories);
    model.addAttribute(BUDGET, budget);

    session.setAttribute(USER, user);
    session.setAttribute("link", "budgets/" + budgetId + "/editBudget");
    model.addAttribute("newBudget", new Budget());

    return "editBudget";
  }

  @RequestMapping(value = "/budgets/{budgetId}/editBudget", method = RequestMethod.POST)
  public String postEditBudget(HttpSession session, HttpServletRequest request, @PathVariable("budgetId") Long budgetId,
                               @Valid @ModelAttribute("newBudget") Budget budget) {
    budget.setAmount(BigDecimal.valueOf(0));

    if (request.getParameter(NAME).isEmpty() || request.getParameter(ACCOUNT).isEmpty()
        || request.getParameter(CATEGORY).isEmpty()) {
      return "editBudget";
    }

    User user = (User) session.getAttribute(USER);
    Budget oldBudget = budgetService.getBudgetByBudgetId(budgetId);
    Account acc = accountService.getAccountByAccountName(request.getParameter(ACCOUNT));
    Category category = categoryService.getCategoryByCategoryName(request.getParameter(CATEGORY));
    String date = request.getParameter(DATE);

    budgetService.postEditBudget(budgetId, budget, user, oldBudget, acc, category, date);

    return "redirect:/budgets/" + budgetId;
  }

  @RequestMapping(value = "/budgets/{budgetId}", method = RequestMethod.GET)
  public String viewBudget(@PathVariable("budgetId") Long budgetId, Model model) {
    List<Transaction> transactionsPaged = budgetService.getPagingTransactions(budgetId, 1);
    int allCount = budgetService.getBudgetTransactions(budgetId).size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    model.addAttribute(BUDGET_ID, budgetId);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("pages", pages);
    return "budgetInfo";

  }

  @RequestMapping(value = "/budgets/{budgetId}/delete", method = RequestMethod.POST)
  public String deleteBudget(@PathVariable("budgetId") Long budgetId, HttpSession session) {
    User user = (User) session.getAttribute(USER);
    Budget budget = budgetService.getBudgetByBudgetId(budgetId);
    budgetService.deleteBudget(budget);
    return "redirect:/budgets";
  }

  @GetMapping(value = "/budgets/{budgetId}/{page}")
  public String transactionPaging(@PathVariable("budgetId") Long budgetId, @PathVariable("page") int page, Model model) {
    List<Transaction> transactionsPaged = budgetService.getPagingTransactions(budgetId, page);
    int allCount = budgetService.getBudgetTransactions(budgetId).size();
    int pages = (int) Math.ceil(allCount / (double) 10);

    model.addAttribute(BUDGET_ID, budgetId);
    model.addAttribute("pagedTransactions", transactionsPaged);
    model.addAttribute("pages", pages);

    return "transactions";
  }
}
