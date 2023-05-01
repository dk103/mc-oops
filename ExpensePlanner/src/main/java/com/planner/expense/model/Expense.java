package com.planner.expense.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@Getter
@Builder(toBuilder = true)
public class Expense {

    public static BiFunction<ExpenseSplitter, Expense, Map<String, Double>> calculateFunc =
            (splitter, expense) -> splitter.doCalculate(expense);

    private String id;
    private String categoryId;
    private double amt;
    private User paidBy;
    private Collection<String> usersInvolved;
    @Builder.Default
    private Map<String, Integer> shareInPer = new ConcurrentHashMap<>();
    @Builder.Default
    private Map<String, Double> sharesInAmt = new ConcurrentHashMap<>();
    @Builder.Default
    private ExpenseSplitter splitMode = SplitMode.EQUAL;



}
