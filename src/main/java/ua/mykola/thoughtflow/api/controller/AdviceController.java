package ua.mykola.thoughtflow.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.mykola.thoughtflow.api.dto.ErrorMessage;
import ua.mykola.thoughtflow.exception.DuplicateException;
import ua.mykola.thoughtflow.exception.InvalidPasswordException;
import ua.mykola.thoughtflow.exception.InvalidTokenException;
import ua.mykola.thoughtflow.exception.NotFoundException;

import java.util.stream.Collectors;


@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorMessage> handleInvalidPasswordException(DuplicateException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(errorMessage)
                        .build());
    }
}
