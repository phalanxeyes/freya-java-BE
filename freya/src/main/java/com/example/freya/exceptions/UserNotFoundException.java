package com.example.freya.exceptions;

public class UserNotFoundException extends Exception{
	public UserNotFoundException(Integer id){
        super("User with ID: " + id + " not found");
    }
}
