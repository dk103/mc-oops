package com.planner.expense.repo.impl;

import com.planner.expense.model.Expense;
import com.planner.expense.repo.AbstractDaoImpl;
import com.planner.expense.repo.IRepository;

import java.util.Objects;

public class ExpenseRepo extends AbstractDaoImpl<String, Expense> {

    public ExpenseRepo(IRepository<String, Expense, ? extends IRepository> repository) {
        super(repository);
    }

    @Override
    protected Expense doCreate(Expense object) {
        getRepository().save(object, object.getId());
        return object;
    }

    @Override
    protected Expense doUpdate(Expense object) {
        Expense existing = getRepository().get(object.getId());
        if (Objects.isNull(existing)) {
            throw new IllegalArgumentException("Expense not found with id: " +  object.getId());
        }
        Expense modified = existing.toBuilder()
                .paidBy(object.getPaidBy())
                .amt(object.getAmt())
                .categoryId(object.getCategoryId())
                .shareInPer(object.getShareInPer())
                .splitMode(object.getSplitMode())
                .usersInvolved(object.getUsersInvolved())
                .sharesInAmt(object.getSharesInAmt())
                .build();
        getRepository().save(modified, object.getId());
        return modified;
    }

    @Override
    protected Expense doFind(String name) {
        throw new UnsupportedOperationException("operation not supported");
    }
}
