package com.planner.expense.model;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Builder(toBuilder = true)
@ToString
public class Trip {
    private String tripId;
    private String tripName;
    private Collection<User> users;

    public void addParticipants(User user) {
        if (getUsers().contains(user)) {
            return;
        }
        users.add(user);
    }

    public void removeParticipants(User user) {
        if (!getUsers().contains(user)) {
            return;
        }
        users.remove(user);
    }
}
