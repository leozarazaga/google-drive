package com.example.googledrive.exception;

/**
 * Custom exception indicating that the upload size limit for a file has been exceeded.
 * Extends the RuntimeException class for unchecked exception handling.
 */
public class MaxUploadSizeExceededException extends RuntimeException{
    public MaxUploadSizeExceededException(String fileName) {
        super("The file '" + fileName + "' is too large!");
    }
}
