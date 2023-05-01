package com.planner.expense.repo.impl;

import com.planner.expense.model.User;
import com.planner.expense.repo.AbstractDaoImpl;
import com.planner.expense.repo.IRepository;

import java.util.Objects;

public class UserRepo extends AbstractDaoImpl<String, User> {

    public UserRepo(IRepository<String, User, ? extends IRepository> repository) {
        super(repository);
    }

    @Override
    protected User doCreate(User object) {
        getRepository().save(object, object.getUserId());
        return object;
    }

    @Override
    protected User doUpdate(User object) {
        User existing = getRepository().get(object.getUserId());
        if (Objects.isNull(existing)) {
            throw new IllegalArgumentException("User not found with id: " +  object.getUserId());
        }
        User modified = existing.toBuilder()
                .userName(object.getUserName())
                .mobileNo(object.getMobileNo())
                .build();
        getRepository().save(modified, object.getUserId());
        return modified;
    }

    @Override
    protected User doFind(String name) {
        return getRepository().getAll()
                .stream()
                .filter(user -> name.equals(user.getUserName()))
                .findFirst()
                .orElse(null);
    }
}
