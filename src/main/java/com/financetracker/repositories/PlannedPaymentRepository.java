package com.financetracker.repositories;

import com.financetracker.entities.PlannedPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by blagoy
 */
public interface PlannedPaymentRepository extends JpaRepository<PlannedPayment, Long> {

    List<PlannedPayment> findAll();

    List<PlannedPayment> findByAccountAccountId(long accountId);

    List<PlannedPayment> findByCategoryCategoryId(long categoryId);

    PlannedPayment findByPlannedPaymentId(long plannedPaymentId);

//    List<PlannedPayment> findByAccountUser(User user);

    List<PlannedPayment> findAllByFromDate(LocalDateTime localDateTime);

    Set<PlannedPayment> findAllByDescriptionContaining(String keyword);
}
