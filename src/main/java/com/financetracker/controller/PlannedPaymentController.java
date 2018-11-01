package com.financetracker.controller;

import com.financetracker.entities.Account;
import com.financetracker.entities.Category;
import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.User;
import com.financetracker.services.AccountService;
import com.financetracker.services.CategoryService;
import com.financetracker.services.PlannedPaymentService;
import com.financetracker.services.UserService;
import com.financetracker.util.DateConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Controller
public class PlannedPaymentController {
    public static final String ERROR_MESSAGE = "Could not insert planned payment. Please, enter valid data!";
    public static final String DATE_MESSAGE = "Please enter a date greater than today's date";

    @Autowired
    private PlannedPaymentService plannedPaymentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/plannedPayments", method = RequestMethod.GET)
    public String getAllPlannedPayments(HttpServletRequest request, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

//        Set<Category> allCategories = new HashSet<Category>();
        Set<Category> allCategories = categoryService.getAllCategories();
        List<PlannedPayment> plannedPaymentsPaged = plannedPaymentService.getPagingPlannedPayments(user, 1);
        List<PlannedPayment> plannedPayments = plannedPaymentService.getAllPlannedPaymentsByUser(user);
        int pages = (int) Math.ceil(plannedPayments.size() / (double) 10);
//        Set<Category> categories = categoryService.getAllCategoriesByUserId();
//        Set<Category> ownCategories = categoryService.getAllCategoriesByUserId(user.getUserId());
//        allCategories.addAll(categories);
//        allCategories.addAll(ownCategories);
        Set<Account> accounts = accountService.getAllAccounts();

        request.getSession().setAttribute("plannedPayments", plannedPayments);
        request.getSession().setAttribute("accounts", accounts);
        request.getSession().setAttribute("categories", allCategories);
        model.addAttribute("pagedPlannedPayments", plannedPaymentsPaged);
        model.addAttribute("pages", pages);

        return "plannedPayments";
    }

    @RequestMapping(value = "/addPlannedPayment", method = RequestMethod.GET)
    public String getAddPlannedPayment(HttpSession session, Model model) {
        PlannedPayment plannedPayment = new PlannedPayment();
        model.addAttribute("plannedPayment", plannedPayment);
        session.setAttribute("link", "addPlannedPayment");
        return "addPlannedPayment";
    }

    @RequestMapping(value = "/addPlannedPayment", method = RequestMethod.POST)
    public String postAddPlannedPayment(HttpServletRequest request, Model model,
                                        @Valid @ModelAttribute("plannedPayment") PlannedPayment plannedPayment, BindingResult bindingResult) {
        String type = request.getParameter("type");
        String account = request.getParameter("account");
        String category = request.getParameter("category");
        String date = request.getParameter("date");
        String name = request.getParameter("name");
        String amount = request.getParameter("amount");
        User user = (User) request.getSession().getAttribute("user");

        if (type.isEmpty() || account.isEmpty() || category.isEmpty() || date.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("error", ERROR_MESSAGE);
            PlannedPayment payment = new PlannedPayment();
            return "addPlannedPayment";
        }

        LocalDateTime newDate = DateConverters.convertFromStringToLocalDateTime(date);
        if (LocalDateTime.now().isAfter(newDate)) {
            model.addAttribute("error", DATE_MESSAGE);
            PlannedPayment payment = new PlannedPayment();
            return "addPlannedPayment";
        }

        plannedPaymentService.postPlannedPayment(user, account, category, name, type, newDate, amount, plannedPayment, 0);
        Account acc = accountService.getAccountByAccountName(account);

        request.setAttribute("user", user);
        request.setAttribute("accountId", acc.getAccountId());

        return "redirect:/plannedPayments";
    }

    @RequestMapping(value = "/payment/{plannedPaymentId}", method = RequestMethod.GET)
    public String getEditPlannedPayment(HttpSession session, Model model,
                                        @PathVariable("plannedPaymentId") Long plannedPaymentId) {
        PlannedPayment plannedPayment = plannedPaymentService.getPlannedPaymentByPlannedPaymentId(plannedPaymentId);
        String name = plannedPayment.getName();
        String type = plannedPayment.getPaymentType().toString();
        String description = plannedPayment.getDescription();
        BigDecimal amount = plannedPayment.getAmount();
        String accountName = accountService.getAccountNameByAccountId(plannedPayment.getAccount().getAccountId());
        String category = categoryService.getCategoryNameByCategoryId(plannedPayment.getCategory().getCategoryId());
        LocalDateTime date = plannedPayment.getFromDate();

        model.addAttribute("plannedPayment", plannedPayment);
        model.addAttribute("editPlannedPaymentName", name);
        model.addAttribute("editPlannedPaymentType", type);
        model.addAttribute("editTPlannedPaymentDescription", description);
        model.addAttribute("editPlannedPaymentAmount", amount);
        model.addAttribute("editPlannedPaymentAccount", accountName);
        model.addAttribute("editPlannedPaymentCategory", category);
        model.addAttribute("editPlannedPaymentDate", date);

        session.setAttribute("link", "payment/" + plannedPaymentId);
        session.setAttribute("plannedPaymentId", plannedPaymentId);

        return "editPlannedPayment";
    }

    @RequestMapping(value = "/payment/editPlannedPayment", method = RequestMethod.POST)
    public String postEditPlannedPayment(HttpServletRequest request, HttpSession session, Model model,
                                         @Valid @ModelAttribute("plannedPayment") PlannedPayment plannedPayment, BindingResult bindingResult) {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String account = request.getParameter("account");
        String category = request.getParameter("category");
        String amount = request.getParameter("amount");
        String date = request.getParameter("date");

        long plannedPaymentId = (long) request.getSession().getAttribute("plannedPaymentId");

        if (type.isEmpty() || account.isEmpty() || category.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("error", "Could not update payment. Please, enter valid data!");
            try {
                PlannedPayment payment = new PlannedPayment();

                LocalDateTime newDate = DateConverters.convertFromStringToLocalDateTime(date);

                model.addAttribute("editPlannedPaymentName", name);
                model.addAttribute("editPlannedPaymentType", type);
                model.addAttribute("editTPlannedPaymentDescription", plannedPayment.getDescription());
                model.addAttribute("editPlannedPaymentAmount", amount);
                model.addAttribute("editPlannedPaymentAccount", account);
                model.addAttribute("editPlannedPaymentCategory", category);
                model.addAttribute("editPlannedPaymentDate", newDate);
            } catch (Exception e) {
                return "error";
            }

            return "editPlannedPayment";
        }

        LocalDateTime newDate = DateConverters.convertFromStringToLocalDateTime(date);
        if (LocalDateTime.now().isAfter(newDate)) {
            model.addAttribute("error", DATE_MESSAGE);
            PlannedPayment payment = new PlannedPayment();
            return "editPlannedPayment";
        }

        User user = (User) session.getAttribute("user");
        plannedPaymentService.postPlannedPayment(user, account, category, name, type, newDate, amount, plannedPayment, plannedPaymentId);

        return "redirect:/plannedPayments";
    }

    @RequestMapping(value = "/payment/deletePlannedPayment/{plannedPaymentId}", method = RequestMethod.POST)
    public String deletePlannedPayment(@PathVariable("plannedPaymentId") Long plannedPaymentId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        plannedPaymentService.deletePlannedPayment(plannedPaymentId);

        return "redirect:/plannedPayments";
    }

    @RequestMapping(value = "/plannedPayments/{page}", method = RequestMethod.GET)
    public String plannedPaymentPaging(@PathVariable("page") int page, HttpSession session, HttpServletRequest request, Model model) {
        User user = (User) session.getAttribute("user");
        List<PlannedPayment> plannedPaymentsPaged = plannedPaymentService.getPagingPlannedPayments(user, page);
        List<PlannedPayment> plannedPayments = plannedPaymentService.getAllPlannedPaymentsByUser(user);
        int pages = (int) Math.ceil(plannedPayments.size() / (double) 10);
//        Set<Category> allCategories = new HashSet<Category>();
        Set<Category> allCategories = categoryService.getAllCategories();
//        Set<Category> categories = categoryService.getAllCategoriesByUserId();
//        Set<Category> ownCategories = categoryService.getAllCategoriesByUserId(user.getUserId());
//        allCategories.addAll(categories);
//        allCategories.addAll(ownCategories);
        Set<Account> accounts = accountService.getAllAccounts();

        request.getSession().setAttribute("plannedPayments", plannedPayments);
        request.getSession().setAttribute("accounts", accounts);
        request.getSession().setAttribute("categories", allCategories);
        model.addAttribute("pagedPlannedPayments", plannedPaymentsPaged);
        model.addAttribute("pages", pages);

        return "plannedPayments";
    }
}
