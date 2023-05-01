package com.planner.expense.model;

import lombok.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum SplitMode implements ExpenseSplitter {
   EQUAL {
      @Override
      public Map<String, Double> calculate(double amt, Collection<String> users, String paidBy, Map<String, Integer> percentage) {
         if (amt == 0d) {
            return Collections.EMPTY_MAP;
         }
         double splitEqual = amt/ users.size();
         Map<String, Double> shares = new HashMap<>();
         users.stream().forEach(userId -> {
            if (!userId.equals(paidBy)) {
               shares.put(userId, -1 * splitEqual);
               return;
            }
            shares.put(userId, splitEqual);
         });
         return shares;
      }
   }, PERCENTAGE {
      @Override
      public Map<String, Double> calculate(double amt, Collection<String> users, String paidBy, Map<String, Integer> percentage) {
         if (percentage == null || percentage.isEmpty()) {
            throw new IllegalArgumentException("percentage for user not given");
         }
         if (amt == 0d) {
            return Collections.EMPTY_MAP;
         }

         Map<String, Double> shares = new HashMap<>();
         users.stream().forEach(userId -> {
            if (!userId.equals(paidBy)) {
               shares.put(userId, -1 * percentage.get(userId) * amt);
               return;
            }
            shares.put(userId, percentage.get(userId) * amt);
         });
         return shares;
      }
   };

   public abstract Map<String, Double>  calculate(double amt,
                                                  @NonNull Collection<String> users, @NonNull String paidBy,
                                                  Map<String, Integer> percentage);
   @Override
   public Map<String, Double> doCalculate(Expense expense) {
      Expense currentExpense = expense.toBuilder().build();
      return calculate(currentExpense.getAmt(), currentExpense.getUsersInvolved(), currentExpense.getPaidBy().getUserId(),
              currentExpense.getShareInPer());
   }
}

interface ExpenseSplitter {
   Map<String, Double> doCalculate(@NonNull final Expense expense);
}
