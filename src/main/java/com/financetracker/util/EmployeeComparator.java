package com.financetracker.util;

import com.financetracker.model.User;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<User> {

  @Override
  public int compare(User u1, User u2) {
    if (u1.getFirstName().equals(u2.getFirstName())) {
      return u1.getLastName().compareToIgnoreCase(u2.getLastName());
    }
    return u1.getFirstName().compareToIgnoreCase(u2.getFirstName());
  }
}
