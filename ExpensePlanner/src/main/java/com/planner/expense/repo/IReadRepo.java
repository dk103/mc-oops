package com.planner.expense.repo;

import java.util.Collection;

public interface IReadRepo<IdT, T> {
    Collection<T> getAll();

    T find(String name);

    T get(IdT id);
}
