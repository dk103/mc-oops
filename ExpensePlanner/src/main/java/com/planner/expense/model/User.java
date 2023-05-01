package com.planner.expense.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@ToString
public class User {
    private String userId;
    private String userName;
    private String mobileNo;
    private Set<String> tripParticipation;

    public void addTrip(String tripId) {
        tripParticipation.add(tripId);
    }
    public void remove(String tripId) {
        if (!tripParticipation.contains(tripId)) {
            return;
        }
        tripParticipation.remove(tripId);
    }
}
