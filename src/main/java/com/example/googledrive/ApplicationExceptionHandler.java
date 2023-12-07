package com.example.googledrive;

import com.example.googledrive.exception.*;
import com.example.googledrive.message.ResponseMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the global exception handler for our application. It ensures that we give consistent responses
 * for various exceptions throughout the system. By extending ResponseEntityExceptionHandler,
 * we centralize the exception handling logic for our controllers.
 *
 * Note: The ErrorResponse class is used to encapsulate detailed error information and is sent to
 * this handler for consistent error response generation.
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the situation where a user is not found in the system.
     * Responds with a user-friendly error message and HTTP status 404 (Not Found).
     *
     * @param ex The UserNotFoundException that triggered this handler.
     * @return ResponseEntity containing an ErrorResponse with the error message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the situation where a folder is not found in the system.
     * Responds with a user-friendly error message and HTTP status 404 (Not Found).
     *
     * @param ex The FolderNotFoundException that triggered this handler.
     * @return ResponseEntity containing an ErrorResponse with the error message.
     */
    @ExceptionHandler(FolderNotFoundException.class)
    public ResponseEntity<Object> handleFolderNotFoundException(FolderNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the situation where a search yields no results.
     * Responds with a user-friendly error message and HTTP status 404 (Not Found).
     *
     * @param ex The NoSearchResultFoundException that triggered this handler.
     * @return ResponseEntity containing an ErrorResponse with the error message.
     */
    @ExceptionHandler(NoSearchResultFoundException.class)
    public ResponseEntity<Object> handleNoSearchResultFoundException(NoSearchResultFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the situation where an attempt is made to create a file with an existing name.
     * Responds with a user-friendly error message and HTTP status 404 (Not Found).
     *
     * @param ex The FileAlreadyExistsException that triggered this handler.
     * @return ResponseEntity containing an ErrorResponse with the error message.
     */
    @ExceptionHandler(FileAlreadyExistsException.class)
    public ResponseEntity<Object> handleFileAlreadyExistsException(FileAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the situation where a file is not found in the system.
     * Responds with a user-friendly error message and HTTP status 404 (Not Found).
     *
     * @param ex The FileNotFoundException that triggered this handler.
     * @return ResponseEntity containing an ErrorResponse with the error message.
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the situation where the uploaded file exceeds the maximum allowed size.
     * Responds with a user-friendly error message and HTTP status 417 (Expectation Failed).
     *
     * @param ex The MaxUploadSizeExceededException that triggered this handler.
     * @return ResponseEntity containing an ErrorResponse with the error message.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handles the validation errors for fields annotated with @NotBlank.
     * Responds with a user-friendly error message and HTTP status 400 (Bad Request).
     *
     * @param ex The ConstraintViolationException that triggered this handler.
     * @return ResponseEntity containing an ErrorResponse with the validation error messages.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> errors.add(violation.getMessage()));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
