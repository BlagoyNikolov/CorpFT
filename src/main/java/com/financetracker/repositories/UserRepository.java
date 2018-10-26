package com.financetracker.repositories;

import com.financetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by blagoy
 */
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "UPDATE User SET password = ?1 WHERE userId = ?2")
  void updateUserPassword(User user, String password);

  User findByUsername(String username);

  boolean existsUserByUsername(String username);

  boolean existsUserByEmail(String email);

  User findByEmail(String email);

  boolean existsUserByPasswordToken(String passwordToken);

  User findByPasswordToken(String passwordToken);

  User findByUserId(Long userId);
}
