package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Item is empty")
public class EmptyException extends RuntimeException {
  public EmptyException(String message) {
    super(message);
  }
}
