//package com.affiliateSWD.affiliate_marketing.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//
//public class APIHandleException {
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Object> handleInvalidUserNamePassword(BadCredentialsException e) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//    }
//
//    @ExceptionHandler(AuthenticationServiceException.class)
//    public ResponseEntity<String> handleAuthenticationServiceException(AuthenticationServiceException e) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    }
//
//
//}