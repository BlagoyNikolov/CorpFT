package com.financetracker.services;

import com.financetracker.entities.Category;
import com.financetracker.entities.PaymentType;

import java.util.Set;

/**
 * Created by blagoy
 */
public interface CategoryService {

  String getCategoryNameByCategoryId(long categoryId);

  Category getCategoryByCategoryId(long categoryId);

  //    Set<Category> getAllCategoriesByUserId(Long... userIdParam);

  Category getCategoryByCategoryName(String categoryName);

  Set<Category> getAllCategoriesByType(PaymentType type);

  void postCategory(Category category);

  Set<String> getCategories(String type);

  Set<Category> getAllCategories();
}
