package com.planner.expense.repo;

import com.planner.expense.repo.INamedRepo;

import java.util.Collection;

public interface IRepository<IdT, T, N> extends INamedRepo<N> {
    void save(T Object, IdT id);

    T get(IdT id);

    void remove(IdT id);

    Collection<T> getAll();
}
