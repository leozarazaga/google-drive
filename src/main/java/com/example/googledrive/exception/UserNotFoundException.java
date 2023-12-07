package com.example.googledrive.exception;

/**
 * Custom exception indicating that a user with the specified ID does not exist.
 * Extends the RuntimeException class for unchecked exception handling.
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id) {
        super("A user with id '" + id + "' does not exist");
    }
}
