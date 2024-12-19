//package edu.du.proj_g2e.member.exception;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        List<String> errorMessages = new ArrayList<>();
//        BindingResult bindingResult = ex.getBindingResult();
//
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
//        }
//
//        return ResponseEntity.badRequest().body(errorMessages);
//    }
//}