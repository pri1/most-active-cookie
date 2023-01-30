package com.coding.task.quantcast.cookiefilter.exception;

/** Custom exception for cookie log parsing */
public class LogParsingException extends Exception {
  private String message;
  public LogParsingException(Throwable error, String message) {
    super(error);
    this.message = message;
  }
}
