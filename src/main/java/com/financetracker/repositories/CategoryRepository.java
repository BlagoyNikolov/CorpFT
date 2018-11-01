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

//    Set<Category> findByUserIsNull();

//    Set<Category> findByUserUserId(long userId);

    Category findByName(String categoryName);

    List<Category> findAllByType(PaymentType type);

//    @Query(value = "SELECT c FROM Category c WHERE (user_id = ?1 OR user_id IS NULL) AND type = ?2")
//    Set<Category> getCategories(User user, PaymentType type);
}
