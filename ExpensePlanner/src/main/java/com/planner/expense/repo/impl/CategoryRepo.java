package com.planner.expense.repo.impl;

import com.planner.expense.model.Category;
import com.planner.expense.repo.AbstractDaoImpl;
import com.planner.expense.repo.IRepository;

import java.util.Objects;

public class CategoryRepo extends AbstractDaoImpl<String, Category> {

    public CategoryRepo(IRepository<String, Category, ? extends IRepository> repository) {
        super(repository);
    }

    @Override
    protected Category doCreate(Category object) {
        getRepository().save(object, object.getCategoryId());
        return object;
    }

    @Override
    protected Category doUpdate(Category updatedCategory) {
        Category existing = getRepository().get(updatedCategory.getCategoryId());
        if (Objects.isNull(existing)) {
            throw new IllegalArgumentException("Trip not found with id: " +  updatedCategory.getCategoryId());
        }
        Category modified = existing.toBuilder()
                .categoryId(Category.function.apply(updatedCategory.getTripId(), updatedCategory.getCategoryName()))
                .categoryName(updatedCategory.getCategoryName())
                .tripId(updatedCategory.getTripId())
                .build();
        getRepository().save(modified, updatedCategory.getCategoryId());
        return modified;
    }

    @Override
    protected Category doFind(String name) {
        return getRepository().getAll()
                .stream()
                .filter(category -> name.equals(category.getCategoryName()))
                .findFirst()
                .orElse(null);
    }
}
