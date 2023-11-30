package com.example.googledrive.exception;

public class MaxUploadSizeExceededException extends RuntimeException{
    public MaxUploadSizeExceededException(String fileName) {
        super("The file '" + fileName + "' is too large!");
    }
}
