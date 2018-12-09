package com.financetracker.repositories;

import com.financetracker.entities.Category;
import com.financetracker.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by blagoy
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Category findByCategoryId(long categoryId);

  Category findByName(String categoryName);

  List<Category> findAllByType(PaymentType type);
}
