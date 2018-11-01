package com.financetracker.util;

import com.financetracker.entities.Budget;

import java.time.LocalDateTime;

public class DateConverters {
    public static LocalDateTime convertFromStringToLocalDateTime(String date) {
        String[] inputDate = date.split("/");
        int month = Integer.valueOf(inputDate[0]);
        int dayOfMonth = Integer.valueOf(inputDate[1]);
        int year = Integer.valueOf(inputDate[2]);
        return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
    }

    public static String convertBudgetDate(Budget budget) {
        LocalDateTime fromDate = budget.getFromDate();
        LocalDateTime toDate = budget.getToDate();

        StringBuilder date = new StringBuilder();
        date.append(fromDate.getMonthValue()).append("/").append(fromDate.getDayOfMonth()).append("/").append(fromDate.getYear());
        date.append(" - ");
        date.append(toDate.getMonthValue()).append("/").append(toDate.getDayOfMonth()).append("/").append(toDate.getYear());

        return date.toString();
    }

    public static LocalDateTime[] dateRange(String date) {
        LocalDateTime[] result = new LocalDateTime[2];
        String[] inputDate = date.split("/");
        int monthFrom = Integer.valueOf(inputDate[0]);
        int dayOfMonthFrom = Integer.valueOf(inputDate[1]);

        String[] temp = inputDate[2].toString().split(" - ");
        int yearFrom = Integer.valueOf(temp[0]);
        int monthTo = Integer.valueOf(temp[1]);
        int dayOfMonthTo = Integer.valueOf(inputDate[3]);
        int yearTo = Integer.valueOf(inputDate[4]);

        LocalDateTime dateFrom = LocalDateTime.of(yearFrom, monthFrom, dayOfMonthFrom, 0, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(yearTo, monthTo, dayOfMonthTo, 0, 0, 0);
        result[0] = dateFrom;
        result[1] = dateTo;

        return result;
    }
}
