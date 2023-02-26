package com.practice.lld.history;

import com.practice.lld.domain.Contact;

import java.util.Collection;

public interface QuickDialTracker {

    Collection<Contact> getListed();
    void addIntoTracker(Contact object);

}
