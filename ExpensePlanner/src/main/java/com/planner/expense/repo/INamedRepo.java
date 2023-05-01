package com.planner.expense.repo;

public interface INamedRepo<T> {
    String getRepoName();
    Class<T> getRepoInstance();
}
