package com.practice.lld.core.impl;

import com.practice.lld.core.IContactService;
import com.practice.lld.domain.Contact;
import com.practice.lld.exception.EntityWriteException;
import com.practice.lld.exception.NotFoundException;
import com.practice.lld.repository.IRepository;

import java.util.Collection;
import java.util.UUID;

public class StandardContactService implements IContactService<Contact> {
    private final IRepository<Contact> repository;

    private StandardContactService(IRepository<Contact> repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Contact> getAll() {
        return this.repository.getAll();
    }

    @Override
    public void delete(UUID id) {
        this.repository.delete(id.toString());
    }

    @Override
    public void update(Contact recent) throws NotFoundException {
        this.repository.update(recent);
    }

    @Override
    public Contact findByPhone(String phone) throws NotFoundException {
        return this.repository.findByPhoneNo(phone);
    }

    @Override
    public void add(Contact contact) throws EntityWriteException {
        this.repository.save(contact);
    }
}
