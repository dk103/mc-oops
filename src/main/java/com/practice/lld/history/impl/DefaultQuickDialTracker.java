package com.practice.lld.history.impl;

import com.practice.lld.domain.Contact;
import com.practice.lld.exception.NotFoundException;
import com.practice.lld.history.DialListingStrategy;
import com.practice.lld.history.QuickDialTracker;
import com.practice.lld.history.algorithm.LRUListingStrategy;
import com.practice.lld.repository.IRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class DefaultQuickDialTracker implements QuickDialTracker {
    private DialListingStrategy<UUID> dialListingStrategy;
    private final IRepository<Contact> repository;
    private final BiFunction<String, IRepository<Contact>, Contact> ID_RESOLVER = (id, repo) -> {
        try {
            return repo.get(id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    public DefaultQuickDialTracker(String strategyUsed, IRepository repository) {
        this.repository = repository;
        this.dialListingStrategy = new LRUListingStrategy(7);
        assert (strategyUsed.equalsIgnoreCase(dialListingStrategy.strategyMethod())) == true : "some other strategy used";
    }

    @Override
    public Collection<Contact> getListed() {
        return dialListingStrategy.getAll().stream()
                .map(UUID::toString)
                .map(idStr -> ID_RESOLVER.apply(idStr, repository))
                .collect(Collectors.toList());
    }

    @Override
    public void addIntoTracker(Contact object) {
        dialListingStrategy.put(object.getId());
    }
}
