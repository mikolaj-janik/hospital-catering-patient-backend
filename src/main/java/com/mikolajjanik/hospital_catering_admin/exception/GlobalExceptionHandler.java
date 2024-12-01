package com.mikolajjanik.hospital_catering_admin.exception;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final HttpHeaders headers;

    public GlobalExceptionHandler() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @ExceptionHandler(HospitalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHospitalNotFoundException(HospitalNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWardNotFoundException(WardNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DieticianNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDieticianNotFoundException(DieticianNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DietNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDietNotFoundException(DietNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MealNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMealNotFoundException(MealNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordsNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordsNotMatchException(PasswordsNotMatchException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadLoginCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadLoginCredentialsException(BadLoginCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        errors.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotExpiredException(TokenNotExpiredException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DietAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleDietAlreadyExistException(DietAlreadyExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMealTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMealTypeException(InvalidMealTypeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DietMismatchException.class)
    public ResponseEntity<ErrorResponse> handleDietMismatchException(DietMismatchException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DiaryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDiaryNotFoundException(DiaryNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectMealException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectMealException(IncorrectMealException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PremiumMealDiaryException.class)
    public ResponseEntity<ErrorResponse> handlePremiumMealDiaryException(PremiumMealDiaryException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DiaryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDiaryAlreadyExistsException(DiaryAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectTypeException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectTypeException(IncorrectTypeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MealCannotBeDeletedException.class)
    public ResponseEntity<ErrorResponse> handleMealCannotBeDeletedException(MealCannotBeDeletedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DietCannotBeDeletedException.class)
    public ResponseEntity<ErrorResponse> handleDietCannotBeDeletedException(DietCannotBeDeletedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotDeleteDieticianException.class)
    public ResponseEntity<ErrorResponse> handleCannotDeleteDieticianException(CannotDeleteDieticianException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotDeleteWardException.class)
    public ResponseEntity<ErrorResponse> handleCannotDeleteWardException(CannotDeleteWardException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }
}
