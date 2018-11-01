package com.financetracker.services;

import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by blagoy
 */
public interface PlannedPaymentService {

    List<PlannedPayment> getAllPlannedPayments();

    List<PlannedPayment> getAllPlannedPaymentsByAccountId(long accountId);

    List<PlannedPayment> getAllPlannedPaymentsByCategoryId(long categoryId);

    void insertPlannedPayment(PlannedPayment plannedPayment);

    void updatePlannedPayment(PlannedPayment plannedPayment);

    void postPlannedPayment(User user, String account, String category, String name, String type, LocalDateTime date, String amount,
                            PlannedPayment plannedPayment, long plannedPaymentId);

    void deletePlannedPayment(long plannedPaymentId);

    PlannedPayment getPlannedPaymentByPlannedPaymentId(long plannedPaymentId);

    List<PlannedPayment> getAllPlannedPaymentsByUser(User user);

    void plannedPaymentDailyCronJob();

    List<PlannedPayment> getPagingPlannedPayments(User user, int page);
}
