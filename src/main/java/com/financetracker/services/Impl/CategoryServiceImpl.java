package com.financetracker.services.Impl;

import com.financetracker.entities.Category;
import com.financetracker.entities.PaymentType;
import com.financetracker.repositories.CategoryRepository;
import com.financetracker.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public String getCategoryNameByCategoryId(long categoryId) {
    Category category = categoryRepository.findByCategoryId(categoryId);
    return category.getName();
  }

  @Override
  public Category getCategoryByCategoryId(long categoryId) {
    return categoryRepository.findByCategoryId(categoryId);
  }

  @Override
  public Category getCategoryByCategoryName(String categoryName) {
    Optional<Category> existingCategory = getExistingCategory(categoryName);
    if (existingCategory.isPresent()) {
      return existingCategory.get();
    }
    return categoryRepository.findByName(categoryName);
  }

  @Override
  public Set<Category> getAllCategoriesByType(PaymentType type) {
    return categoryRepository.findAllByType(type)
        .stream().collect(Collectors.toSet());
  }

  @Override
  public void postCategory(Category category) {
    //    category.setUser(user);
    categoryRepository.save(category);
  }

  @Override
  public Set<String> getCategories(String type) {
    return getAllCategoriesByType(PaymentType.valueOf(type))
        .stream()
        .map(category -> category.getName())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Category> getAllCategories() {
    return categoryRepository.findAll().stream().collect(Collectors.toSet());
  }

  private Optional<Category> getExistingCategory(String name) {
    return categoryRepository
        .findAll()
        .stream()
        .filter(category -> name.equals(category.getName()))
        .findAny();
  }
}
