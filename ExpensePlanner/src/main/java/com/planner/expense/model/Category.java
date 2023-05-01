package com.planner.expense.model;

import lombok.Builder;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
@Builder(toBuilder = true)
public class Category {

    public static BiFunction<String, String, String> function =
            (name, id) -> new StringBuilder(id).append("_").append(name).toString();

    private String categoryName;
    private String tripId;
    private String categoryId;
}
