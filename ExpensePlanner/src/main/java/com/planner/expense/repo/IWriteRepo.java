package com.planner.expense.repo;

public interface IWriteRepo<T> {

    T create(T object);
    T update(T object);
}
