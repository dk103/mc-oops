package com.practice.lld.history;

import java.util.Collection;

public interface DialListingStrategy<T> {
    void put(T contact);
    Collection<T> getAll();

    String strategyMethod();

}
