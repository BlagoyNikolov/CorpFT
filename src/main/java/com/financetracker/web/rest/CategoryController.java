package com.financetracker.web.rest;

import com.financetracker.entities.Category;
import com.financetracker.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@Controller
public class CategoryController {
  public static final String ERROR_MESSAGE = "Could not create category. Please, enter a valid name and type!";

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
  public String displayCategory(HttpSession session, Model model, HttpServletRequest request) {
    String link = (String) session.getAttribute("link");
    Category category = new Category();
    model.addAttribute("link", link);
    model.addAttribute("category", category);
    return "category";
  }

  @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
  public String addCategory(HttpServletRequest request, HttpSession session, Model viewModel,
                            @Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      viewModel.addAttribute("error", ERROR_MESSAGE);
      return "category";
    }

    //        User user = (User) request.getSession().getAttribute("user");
    categoryService.postCategory(category);
    String link = (String) session.getAttribute("link");
    return "redirect:" + link;
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/account/getCategory/{type}")
  public ResponseEntity getCategoriesAsync(HttpSession session, @PathVariable("type") String type) {
    //        User user = (User) session.getAttribute("user");
    return ResponseEntity.ok(categoryService.getCategories(type));
  }
}
