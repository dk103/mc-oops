package com.planner.expense.core;

import com.planner.expense.model.Trip;
import com.planner.expense.model.User;

import java.util.Collection;


public interface ITripManager {
    Trip createTrip(String tripName);
    Trip getTrip(String tripId);
    boolean addUser(String userName, String tripName);
    Collection<User> getParticipants(String tripName);
}
