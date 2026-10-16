package com.salest.freya.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(Class<?> clazz, Object data){
        super(clazz.getSimpleName() + " with given data: " + data + " already exists");
    }
}
