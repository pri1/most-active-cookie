package com.coding.task.quantcast.cookiefilter.parser;

import java.time.LocalDate;

/** Class to handle command input, which is f: filename, and d: selected date */
public class CommandLineInput {
  private String fileName;
  private LocalDate selectedDate;

  public CommandLineInput(String fileName, LocalDate selectedDate) {
    this.fileName = fileName;
    this.selectedDate = selectedDate;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public LocalDate getSelectedDate() {
    return selectedDate;
  }

  public void setSelectedDate(LocalDate selectedDate) {
    this.selectedDate = selectedDate;
  }
}
