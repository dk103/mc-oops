package com.planner.expense.core;


import com.planner.expense.model.Expense;
import java.util.Collection;
import java.util.Map;

public interface IExpenseManager {
    Expense createExpense(String tripName, String paidBy, String splitMode, Collection<String> shareInPercentage,
                          Collection<String> usersInvolved);
    Collection<Expense> getAllExpensePaidByUser(String username);
    double overallExpense(String tripName);
    Map<String, Double> groupExpenseByCategory(String categoryName, String tripName);

}
