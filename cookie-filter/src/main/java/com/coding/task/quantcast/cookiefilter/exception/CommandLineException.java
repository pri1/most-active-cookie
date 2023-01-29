package com.coding.task.quantcast.cookiefilter.exception;


public abstract class CommandLineException extends RuntimeException {

  protected CommandLineException() {
  }

  protected CommandLineException(String message) {
    super(message);
  }
}