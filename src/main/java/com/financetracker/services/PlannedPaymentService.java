package com.financetracker.services;

import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by blagoy
 */
public interface PlannedPaymentService {

  void deletePlannedPayment(long plannedPaymentId);

  PlannedPayment getPlannedPaymentByPlannedPaymentId(long plannedPaymentId);

  List<PlannedPayment> getAllPlannedPaymentsByUser(User user);

  List<PlannedPayment> getPagingPlannedPayments(User user, int page);

  void addPlannedPayment(User user, String account, String category, String name, String type, LocalDateTime newDate, String amount,
                         String currencyId, PlannedPayment plannedPayment);

  void editPlannedPayment(User user, String account, String category, String name, String type, LocalDateTime newDate, String amount,
                          String currencyId, PlannedPayment plannedPayment, Long plannedPaymentId);
}
