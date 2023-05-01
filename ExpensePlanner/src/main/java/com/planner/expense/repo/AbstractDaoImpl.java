package com.planner.expense.repo;

import java.util.Collection;

public abstract class AbstractDaoImpl<IdT, T> implements IWriteRepo<T>, IReadRepo<IdT, T> {

    private IRepository<IdT, T, ? extends IRepository> repository;

    public AbstractDaoImpl(IRepository<IdT, T, ? extends IRepository> repository) {
        this.repository = repository;
    }

    @Override
    public T create(T object) {
        return doCreate(object);
    }

    @Override
    public T update(T object) {
        return doUpdate(object);
    }

    protected abstract T doCreate(T object);
    protected abstract T doUpdate(T object);
    protected abstract T doFind(String name);

    public IRepository<IdT, T, ? extends IRepository> getRepository() {
        return repository;
    }

    @Override
    public Collection<T> getAll() {
        return repository.getAll();
    }

    @Override
    public T find(String name) {
        return doFind(name);
    }

    @Override
    public T get(IdT id) {
        return repository.get(id);
    }


}
