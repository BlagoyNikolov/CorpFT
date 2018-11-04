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

  PlannedPayment findByPlannedPaymentId(long plannedPaymentId);

  List<PlannedPayment> findAllByFromDate(LocalDateTime localDateTime);

  Set<PlannedPayment> findAllByDescriptionContaining(String keyword);
}
