package com.example.googledrive.exception;

/**
 * Custom exception indicating that a file already exists for the user in the specified folder.
 * Extends the RuntimeException class for unchecked exception handling.
 */
public class FileAlreadyExistsException extends RuntimeException{
    public FileAlreadyExistsException(String userId, String folderId) {
        super("File already exists for the user in the folder");
    }
}
