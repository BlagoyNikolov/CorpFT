package com.financetracker.controller;

import com.financetracker.entities.Budget;
import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Controller
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request, Model model, HttpSession session) {
        String keyword = request.getParameter("keyword");
        if (!searchService.isKeywordValid(keyword)) {
            return "searchResults";
        }

        User user = (User) session.getAttribute("user");
        Set<Transaction> transactions = searchService.getAllTransactionsByKeyword(keyword);
        Set<PlannedPayment> plannedPayments = searchService.getAllPlannedPaymentsByKeyword(keyword);
        Map<Budget, BigDecimal> budgets = searchService.getAllBudgetsByKeywordAndUser(keyword, user);

        model.addAttribute("transactions", transactions);
        model.addAttribute("plannedPayments", plannedPayments);
        model.addAttribute("budgets", budgets);

        return "searchResults";
    }
}
