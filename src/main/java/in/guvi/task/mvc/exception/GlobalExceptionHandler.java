package in.guvi.task.mvc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Handle Custom "Not Found" Exceptions (e.g., Student ID doesn't exist)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource Not Found: {}", ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Resource Not Found");
    }

    // 2. Handle Database Constraint Violations (e.g., Duplicate Email)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConstraintViolation(DataIntegrityViolationException ex) {
        logger.error("Database constraint violation: {}", ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.CONFLICT, "Database conflict: Likely a duplicate entry or missing foreign key.");
    }

    // 3. Handle Bad Request / Validation Errors (e.g., Invalid form data)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("Validation error: {}", ex.getMessage());
        // Extracting just the first validation error message for simplicity
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return buildErrorResponse(new RuntimeException(errorMessage), HttpStatus.BAD_REQUEST, "Validation Failed");
    }

    // 4. Handle Type Mismatch (e.g., passing a String instead of a Long in the URL)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.error("Type mismatch: {}", ex.getMessage());
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Invalid parameter type in URL.");
    }

    // 5. Catch-All for anything else (True 500 Internal Server Errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        // Log the full stack trace for developers to fix the bug
        logger.error("Unexpected System Error occurred", ex);

        // Hide the stack trace from the user, give a generic message
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected internal server error occurred. Please contact support.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to keep code DRY
    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String customErrorTitle) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(customErrorTitle)
                .message(ex.getMessage()) // Send the specific exception message to the client
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}