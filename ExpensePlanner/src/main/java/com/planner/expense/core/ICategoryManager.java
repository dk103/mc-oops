package com.planner.expense.core;

import com.planner.expense.model.Category;

public interface ICategoryManager {
    Category createCategory(String categoryName, String tripName);
    Category getCategory(String categoryId);
}
