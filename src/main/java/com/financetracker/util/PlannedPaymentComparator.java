package com.financetracker.util;

import com.financetracker.model.PlannedPayment;

import java.util.Comparator;

public class PlannedPaymentComparator implements Comparator<PlannedPayment> {
    @Override
    public int compare(PlannedPayment p1, PlannedPayment p2) {
        if (p1.getFromDate().compareTo(p2.getFromDate()) == 0) {
            return Long.compare(p1.getPlannedPaymentId(), p2.getPlannedPaymentId());
        }
        return p1.getFromDate().compareTo(p2.getFromDate());
    }
}
