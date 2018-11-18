package com.financetracker.rest;

import com.financetracker.entities.Account;
import com.financetracker.entities.User;
import com.financetracker.services.AccountService;
import com.financetracker.services.ChartService;
import com.financetracker.services.UserService;
import com.financetracker.util.EmailSender;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

@Controller
public class UserController {
  public static final String COULD_NOT_LOGIN_MESSAGE = "Could not login. Please, enter a valid username and password!";
  public static final String COULD_NOT_CREATE_PROFILE_MESSAGE = "Could not create profile. Please, enter a valid username and password!";
  public static final String PASSWORD_MISMATCH_MESSAGE = "Password mismatch";
  public static final String COULD_NOT_EDIT_PROFILE_ENTER_CORRECT_DATA =
      "Could not edit your profile. Please, enter a correct data!";
  public static final String ENTER_VALID_DATE_MESSAGE = "Enter valid date";
  public static final String SUBJECT_TEXT_WELCOME = "Welcome to the Finance Tracker";
  public static final String PROFILE_READY = "Your new profile is ready. Track away!";
  public static final String SUBJECT_TEXT_FORGOTTEN_PASSWORD = "FT FORGOTTEN PASSWORD";
  public static final String FORGOTTEN_PASSWORD_EMAIL_TEXT = "Hello %s Click on the link to change password: http://localhost:8080/resetPassword/%s";

  @Autowired
  private UserService userService;

  @Autowired
  private ChartService chartService;

  @Autowired
  private AccountService accountService;

  @GetMapping("/")
  public String home() {
    return "redirect:index";
  }

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String welcome(HttpSession session, Model viewModel) {
    if (session.getAttribute("user") == null) {
      User user = new User();
      viewModel.addAttribute("user", user);
      return "login";
    } else {
      return "redirect:main";
    }
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(HttpSession session, Model viewModel, @ModelAttribute("user") User user) {
    if (userService.isValidLogin(user.getUsername(), user.getPassword())) {
      User existingUser = userService.getUser(user.getUsername());
      session.setAttribute("user", existingUser);
      return "redirect:main";
    } else {
      session.setAttribute("logged", false);
      viewModel.addAttribute("login", COULD_NOT_LOGIN_MESSAGE);
      User newUser = new User();
      viewModel.addAttribute("user", newUser);
      return "login";
    }
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(HttpServletRequest request, HttpSession session, Model viewModel,
                         @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      viewModel.addAttribute("register", COULD_NOT_CREATE_PROFILE_MESSAGE);
      User newUser = new User();
      viewModel.addAttribute("user", newUser);
      return "register";
    }

    String repeatPassword = request.getParameter("repeatPassword");
    if (!MessageDigest.isEqual(DigestUtils.sha512(repeatPassword), user.getPassword())) {
      request.setAttribute("error", PASSWORD_MISMATCH_MESSAGE);
      viewModel.addAttribute("register", PASSWORD_MISMATCH_MESSAGE);
      return "register";
    }

    if (!userService.existsUser(user.getUsername())) {
      user.setPasswordToken(DigestUtils.sha512Hex(DigestUtils.sha512Hex(user.getPassword()) + user.getPassword()));
      user.setAdmin(false);
      userService.insertUser(user);
      //            EmailSender.sendSimpleEmail(user.getEmail(), SUBJECT_TEXT_WELCOME, PROFILE_READY);
      session.setAttribute("user", user);
      return "main";
    }
    return "login";
  }

  @RequestMapping(value = "/main", method = RequestMethod.GET)
  public String main(HttpSession session, Model viewModel) {
    User user = (User) session.getAttribute("user");
    TreeSet<Account> accounts = chartService.getAllAccounts(user);
    BigDecimal allBalance = chartService.calculateAllBalance(accounts);
    BigDecimal allAccountsBalance = accountService.calculateAllAccountBalance();
    Map<LocalDate, BigDecimal> finalDefaultTransactions = chartService.getGraphData(user, allBalance);
    String balance = NumberFormat.getCurrencyInstance(Locale.GERMANY).format(allAccountsBalance);
    viewModel.addAttribute("accounts", accounts);
    viewModel.addAttribute("balance", balance);
    viewModel.addAttribute("defaultTransactions", finalDefaultTransactions);

    return "main";
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String getLogin(Model viewModel) {
    User user = new User();
    viewModel.addAttribute("user", user);
    return "login";
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String getRegister(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "register";
  }

  @RequestMapping(value = "/about", method = RequestMethod.GET)
  public String about() {
    return "about";
  }

  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout(HttpSession session) {
    session.invalidate();
    return "forward:index";
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public String user(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    String username = user.getUsername();
    String email = user.getEmail();
    String firstName = user.getFirstName();
    String lastName = user.getLastName();

    model.addAttribute("username", username);
    model.addAttribute("email", email);
    model.addAttribute("firstName", firstName);
    model.addAttribute("lastName", lastName);
    model.addAttribute("user", new User());
    session.setAttribute("userId", user.getUserId());

    return "user";
  }

  @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
  public String updateUser(HttpSession session, Model model,
                           @Valid @ModelAttribute("user") User newUser, BindingResult bindingResult) {
    User user = (User) session.getAttribute("user");

    if (!MessageDigest.isEqual(DigestUtils.sha512(DigestUtils.sha512Hex(newUser.getPassword())), user.getPassword())
        || bindingResult.hasErrors()) {
      model.addAttribute("editUser", COULD_NOT_EDIT_PROFILE_ENTER_CORRECT_DATA);
      String username = user.getUsername();
      String email = user.getEmail();
      String firstName = user.getFirstName();
      String lastName = user.getLastName();

      model.addAttribute("username", username);
      model.addAttribute("email", email);
      model.addAttribute("firstName", firstName);
      model.addAttribute("lastName", lastName);
      model.addAttribute("user", new User());
      session.setAttribute("userId", user.getUserId());

      return "user";
    }

    user.setEmail(newUser.getEmail());
    user.setFirstName(newUser.getFirstName());
    user.setLastName(newUser.getLastName());

    userService.updateUser(user);
    session.setAttribute("user", user);

    return "redirect:/main";
  }

  @RequestMapping(value = "/forgottenPassword", method = RequestMethod.GET)
  public String forgottenPassword() {
    return "forgottenPassword";
  }

  @RequestMapping(value = "/forgottenPassword", method = RequestMethod.POST)
  public String sendEmail(HttpServletRequest request, Model model) {
    String email = request.getParameter("email");
    if (userService.existEmail(email)) {
      User user = userService.getUserByEmail(email);
      EmailSender.sendSimpleEmail(email, SUBJECT_TEXT_FORGOTTEN_PASSWORD, String.format(FORGOTTEN_PASSWORD_EMAIL_TEXT, user.getFirstName(),
          user.getPasswordToken()));
    } else {
      model.addAttribute("forgottenPassword", "Invalid email");
    }

    return "forgottenPassword";
  }

  @RequestMapping(value = "/resetPassword/{passwordToken}", method = RequestMethod.GET)
  public String resetPassword(Model model, @PathVariable("passwordToken") String passwordToken) {
    model.addAttribute("token", passwordToken);
    return "resetPassword";
  }

  @RequestMapping(value = "/resetPassword/{passwordToken}", method = RequestMethod.POST)
  public String getNewPassword(HttpServletRequest request, Model model, @PathVariable("passwordToken") String passwordToken) {
    String password = request.getParameter("password");
    String repeatPassword = request.getParameter("repeatPassword");

    if (userService.existUserByPasswordToken(passwordToken) && password.equals(repeatPassword)) {
      User user = userService.getUserByPasswordToken(passwordToken);
      userService.updateUserPassword(user, password);
    } else {
      model.addAttribute("ressetPassword", ENTER_VALID_DATE_MESSAGE);
      return "resetPassword";
    }

    User user = new User();
    model.addAttribute("user", user);
    return "redirect:/login";
  }
}
