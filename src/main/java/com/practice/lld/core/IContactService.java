package com.practice.lld.core;


import com.practice.lld.exception.EntityWriteException;
import com.practice.lld.exception.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IContactService<T> {

     Collection<T> getAll();

     void delete(UUID id);

     void update(T recent) throws NotFoundException;

     T findByPhone(String phone) throws NotFoundException;

     void add(T contact) throws EntityWriteException;

}
