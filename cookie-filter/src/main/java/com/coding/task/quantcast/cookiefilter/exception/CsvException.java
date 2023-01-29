package com.coding.task.quantcast.cookiefilter.exception;

public class CsvException {

  public static class InvalidCsvException extends Exception {
    public InvalidCsvException(String message) {
      super(message);
    }
  }
}
