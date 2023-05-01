package com.planner.expense.core;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

public interface IExpenseSummary {
    ExpenseSummary getSummary(String tripName);

    @Builder
    @Getter
    @ToString
    class ExpenseSummary {
        String tripName;
        double totalExpense;
        Map<String, Double> categoryWiseExpense;
        Map<String, Double> userOwesToGrp;
        Map<String, Double> grpOwesToUser;
    }
}
