package com.planner.expense.repo.datastore;

import com.planner.expense.repo.IRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public  class InMemoryRepository<IdT, T> implements IRepository<IdT, T, InMemoryRepository> {
    private Map<IdT, T> store = new ConcurrentHashMap<>();

    @Override
    public String getRepoName() {
        return "in-mem";
    }

    @Override
    public void save(T object, IdT id) {
        Objects.requireNonNull(object);
        store.putIfAbsent(id, object);
    }

    @Override
    public T get(IdT id) {
        return store.get(id);
    }

    @Override
    public void remove(IdT id) {
        if (!store.containsKey(id)) {
            return;
        }
        store.remove(id);
    }

    @Override
    public Collection<T> getAll() {
        return store.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    @Override
    public Class<InMemoryRepository> getRepoInstance() {
        return (Class<InMemoryRepository>) this.getClass();
    }
}
