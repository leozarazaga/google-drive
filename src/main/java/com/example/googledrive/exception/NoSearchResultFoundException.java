package com.example.googledrive.exception;

/**
 * Custom exception indicating that no search result was found for the specified query.
 * Extends the RuntimeException class for unchecked exception handling.
 */
public class NoSearchResultFoundException extends RuntimeException{
    public NoSearchResultFoundException(String search) {
        super("No search result found for the search '" + search + "'");
    }
}
