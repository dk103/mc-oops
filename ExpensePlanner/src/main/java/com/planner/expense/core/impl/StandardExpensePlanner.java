package com.planner.expense.core.impl;

import com.planner.expense.core.IExpensePlanner;
import com.planner.expense.model.Category;
import com.planner.expense.model.Expense;
import com.planner.expense.model.Trip;
import com.planner.expense.model.User;
import com.planner.expense.repo.impl.CategoryRepo;
import com.planner.expense.repo.impl.ExpenseRepo;
import com.planner.expense.repo.impl.TripRepo;
import com.planner.expense.repo.impl.UserRepo;

import java.util.*;

public class StandardExpensePlanner implements IExpensePlanner {
    private TripRepo tripRepo;
    private CategoryRepo categoryRepo;
    private UserRepo userRepo;
    private ExpenseRepo expenseRepo;
    public StandardExpensePlanner(TripRepo tripRepo, CategoryRepo categoryRepo, UserRepo userRepo, ExpenseRepo expenseRepo) {
        this.tripRepo = tripRepo;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
        this.expenseRepo = expenseRepo;
    }

    @Override
    public Category createCategory(String categoryName, String tripName) {
        Trip trip = tripRepo.find(tripName);
        Category category = Category.builder()
                .categoryName(categoryName)
                .categoryId(Category.function.apply(trip.getTripId(), categoryName))
                .build();
        return categoryRepo.create(category);
    }

    @Override
    public Category getCategory(String categoryId) {
        return categoryRepo.get(categoryId);
    }

    @Override
    public ExpenseSummary getSummary(String tripName) {
        return null;
    }

    @Override
    public Trip createTrip(String tripName) {
        Trip existing = tripRepo.find(tripName);
        if (existing != null) {
            throw new IllegalArgumentException("Trip already exist with name: " + tripName);
        }
        return tripRepo.create(Trip.builder()
                .tripName(tripName)
                .build()
        );
    }

    @Override
    public Trip getTrip(String tripId) {
        return tripRepo.get(tripId);
    }

    @Override
    public boolean addUser(String userName, String tripName) {
        Trip existingTrip = tripRepo.find(tripName);
        if (existingTrip == null) {
            throw new IllegalArgumentException("Trip not exists");
        }
        User user = userRepo.find(userName);
        Trip trip = existingTrip.toBuilder()
                .build();
        trip.addParticipants(user);
        tripRepo.update(trip);
        return true;
    }

    @Override
    public Collection<User> getParticipants(String tripName) {
        Trip existingTrip = tripRepo.find(tripName);
        if (existingTrip == null) {
            throw new IllegalArgumentException("Trip not exists");
        }
        return Collections.unmodifiableCollection(existingTrip.getUsers());
    }

    @Override
    public User createUser(String username, String phoneNo) {
        User user = User.builder()
                .userName(username)
                .mobileNo(phoneNo)
                .userId(phoneNo)
                .build();
        return userRepo.create(user);
    }

    @Override
    public Collection<String> getAssociatedTrip(String username) {
        User user = userRepo.find(username);
        return Collections.unmodifiableCollection(user.getTripParticipation());
    }

    @Override
    public void addTripParticipated(String username, String tripName) {
        Trip trip = tripRepo.find(tripName);
        User user = userRepo.find(username);
        Set<String> tripParticipation = new HashSet<>(user.getTripParticipation());
        User modified = user.toBuilder()
                .tripParticipation(tripParticipation)
                .build();
        userRepo.update(user);
    }

    @Override
    public Expense createExpense(String tripName, String paidBy, String splitMode,
                                 Collection<String> shareInPercentage,
                                 Collection<String> usersInvolved) {
        return null;
    }

    @Override
    public Collection<Expense> getAllExpensePaidByUser(String username) {
        return null;
    }

    @Override
    public double overallExpense(String tripName) {
        return 0;
    }

    @Override
    public Map<String, Double> groupExpenseByCategory(String categoryName, String tripName) {
        return null;
    }
}
