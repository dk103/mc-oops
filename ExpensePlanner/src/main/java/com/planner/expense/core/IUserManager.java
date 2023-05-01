package com.planner.expense.core;

import com.planner.expense.model.User;

import java.util.Collection;

public interface IUserManager {
    User createUser(String username, String phoneNo);
    Collection<String> getAssociatedTrip(String username);
    void addTripParticipated(String username, String tripName);
}
