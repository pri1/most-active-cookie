package com.coding.task.quantcast.cookiefilter.exception;

public class CsvErrorException extends RuntimeException {

  private static final long serialVersionUID = 2L;

  /**
   * An exception that stores the exit code for later verification.
   */

  public final int status;

  public CsvErrorException(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

}
