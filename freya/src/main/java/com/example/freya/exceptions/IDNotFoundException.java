package com.example.freya.exceptions;

/**
 * @author Nacho
 */
public class IDNotFoundException extends RuntimeException {
    /**
     * @param clazz pass as example -> Cover.class
     * @author Nacho
     */
    public IDNotFoundException(Class<?> clazz, Integer id){
        super(clazz.getSimpleName() + " with ID: " + id + " not found");
    }
}
