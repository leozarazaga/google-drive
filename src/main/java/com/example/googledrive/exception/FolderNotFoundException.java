package com.example.googledrive.exception;

/**
 * Custom exception indicating that a folder with the specified identifier does not exist.
 * Extends the RuntimeException class for unchecked exception handling.
 */
public class FolderNotFoundException extends RuntimeException{
    public FolderNotFoundException(String id) {
        super("A folder with id '" + id + "' does not exist");
    }
}
