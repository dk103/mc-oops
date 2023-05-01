package com.planner.expense.repo.impl;


import com.planner.expense.model.Trip;
import com.planner.expense.repo.AbstractDaoImpl;
import com.planner.expense.repo.IRepository;

import java.util.Objects;

public class TripRepo extends AbstractDaoImpl<String, Trip> {
    public TripRepo(IRepository<String, Trip, ? extends IRepository> repository) {
        super(repository);
    }

    @Override
    protected Trip doCreate(Trip object) {
        getRepository().save(object, object.getTripId());
        return object;
    }

    @Override
    protected Trip doUpdate(Trip updatedTrip) {
        Trip existing = getRepository().get(updatedTrip.getTripId());
        if (Objects.isNull(existing)) {
            throw new IllegalArgumentException("Trip not found with id: " +  updatedTrip.getTripId());
        }
        Trip modified = existing.toBuilder()
                .tripName(updatedTrip.getTripName())
                .users(updatedTrip.getUsers())
                .build();
        getRepository().save(modified, updatedTrip.getTripId());
        return modified;
    }

    @Override
    protected Trip doFind(String name) {
        return getRepository().getAll()
                .stream()
                .filter(trip -> name.equals(trip.getTripName()))
                .findFirst()
                .orElse(null);
    }

}
