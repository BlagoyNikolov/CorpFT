package com.financetracker.services;

import com.financetracker.entities.User;

/**
 * Created by blagoy
 */
public interface UserService {

  void insertUser(User user);

  void updateUser(User user);

  void updateUserPassword(User user, String password);

  User getUser(String username);

  boolean existsUser(String username);

  boolean isValidLogin(String username, byte[] password);

  boolean existEmail(String email);

  User getUserByEmail(String email);

  boolean existUserByPasswordToken(String token);

  User getUserByPasswordToken(String token);

  User getUserByUserId(long userId);
}
