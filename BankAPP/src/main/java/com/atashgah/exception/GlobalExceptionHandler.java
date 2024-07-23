package com.atashgah.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        logger.error("InsufficientBalanceException: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        logger.error("AccountNotFoundException: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        logger.error("UserAlreadyExistsException: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("UseNotFoundException: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PinSizeException.class)
    public ResponseEntity<String> handlePinSizeException(PinSizeException ex) {
        logger.error("PinSizeException: " + ex.getMessage(), ex);
        return new ResponseEntity<>("Pin should be only 7 characters ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        logger.error("RuntimeException: " + ex.getMessage(), ex);
        return new ResponseEntity<>("An error occurred during the process", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AccountDeactiveException.class)
    public ResponseEntity<String> handleAccountDeactiveException(AccountDeactiveException ex) {
        logger.error("AccountDeactiveException: " + ex.getMessage(), ex);
        return new ResponseEntity<>("Both account should be active", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AccountAlreadyActiveException.class)
    public ResponseEntity<String> handleAlreadyActive(AccountAlreadyActiveException ex) {
        logger.error("RuntimeException: " + ex.getMessage(), ex);
        return new ResponseEntity<>("Specific user has been activated before ", HttpStatus.FOUND);
    }
}