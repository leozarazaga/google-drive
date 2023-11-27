package com.example.googledrive.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id) {
        super("A user with id '" + id + "' does not exist");
    }
}
