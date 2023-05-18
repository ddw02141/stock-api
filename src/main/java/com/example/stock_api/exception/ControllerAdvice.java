package com.example.stock_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(NoSuchMethodException.class)
  public ResponseEntity<Object> noSuchMethodExceptionHandler(NoSuchMethodException ex) {
    log.error(ex.getMessage(), ex);

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
