package com.practice.lld.exception;

import com.practice.lld.domain.AbstractContactT;

public class EntityWriteException extends Throwable {

    private final Class<? extends AbstractContactT> clazz;

    public EntityWriteException(Class<? extends AbstractContactT> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public String getMessage() {
        return "Unable to write entity " + clazz.getTypeName();
    }
}
