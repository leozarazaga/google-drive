package com.example.googledrive.exception;

/**
 * Custom exception indicating that a file with the specified identifier does not exist.
 * Extends the RuntimeException class for unchecked exception handling.
 */
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String id) {
        super("A file with id '" + id + "' does not exist");

    }
}
