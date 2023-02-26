package com.practice.lld.repository;
import com.practice.lld.domain.AbstractContactT;
import com.practice.lld.exception.EntityWriteException;
import com.practice.lld.exception.NotFoundException;
import lombok.NonNull;

import java.util.Collection;


public interface IRepository<T extends AbstractContactT> {

    T save(@NonNull T object) throws EntityWriteException;

    void update(@NonNull T object) throws NotFoundException;

    T get(@NonNull String id) throws NotFoundException;

    T findByPhoneNo(@NonNull String phone) throws NotFoundException;

    boolean isExist(@NonNull String phoneNumber);

    Collection<T> getAll();

    boolean delete(String id);

}
