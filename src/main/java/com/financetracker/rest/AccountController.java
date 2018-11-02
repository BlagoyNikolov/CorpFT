package com.financetracker.rest;

import com.financetracker.entities.Account;
import com.financetracker.entities.User;
import com.financetracker.services.AccountService;
import com.financetracker.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AccountController {
  public static final String COULD_NOT_CREATE_ACCOUNT_PLEASE_ENTER_A_VALID_NAME_AND_AMOUNT =
      "Could not create account. Please, enter a valid name and amount!";

  @Autowired
  private AccountService accountService;

  @Autowired
  private CurrencyService currencyService;

  @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
  public String makeAccount(Model viewModel) {
    Account acc = new Account();
    viewModel.addAttribute("currencies", currencyService.getCurrencyList());
    viewModel.addAttribute("account", acc);
    return "addAccount";
  }

  @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
  public String addAccount(HttpSession session, Model viewModel,
                           @Valid @ModelAttribute("account") Account account, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      viewModel.addAttribute("error", COULD_NOT_CREATE_ACCOUNT_PLEASE_ENTER_A_VALID_NAME_AND_AMOUNT);
      return "addAccount";
    }
    User user = (User) session.getAttribute("user");
    accountService.insertAccount(account, user);
    return "redirect:main";
  }

  @RequestMapping(value = "/account/deleteAccount/{accountId}", method = RequestMethod.POST)
  public String deleteAccount(@Valid @PathVariable("accountId") Long accountId, HttpSession session) {
    User user = (User) session.getAttribute("user");
    accountService.deleteAccount(accountId, user);
    return "redirect:/main";
  }
}
