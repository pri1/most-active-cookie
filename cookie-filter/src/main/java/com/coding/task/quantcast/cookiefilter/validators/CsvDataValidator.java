package com.coding.task.quantcast.cookiefilter.validators;

import static com.coding.task.quantcast.cookiefilter.constants.Constants.DATE_FORMAT;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.DATE_TIME_FORMAT;

import com.coding.task.quantcast.cookiefilter.constants.Constants;
import com.coding.task.quantcast.cookiefilter.exception.CsvException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
public class CsvDataValidator {
  private static final Logger logger = LoggerFactory.getLogger(CsvDataValidator.class);

  private static String inputDate = "";

  public static void validateFileExtension(String fileName) throws Exception {
    if (!Pattern.compile(Constants.FILE_EXTENSION_PATTERN).matcher(fileName).matches()) {
      logger.error("File is not CSV. Please enter a valid CSV file. {}", fileName);
      throw new CsvException.InvalidCsvException(Constants.CSV_FILE_EXTENSION_ERROR_ERROR_MESSAGE);
    }
  }

  public static boolean isValidTimeStamp(String input) {
    try {
      DateTimeFormatter inputFormatter = DateTimeFormatter
          .ofPattern(DATE_TIME_FORMAT, Locale.ENGLISH);
      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH);
      LocalDate date = LocalDate.parse(input, inputFormatter);
      String formattedDate = outputFormatter.format(date);
      inputDate = formattedDate;
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static String dateValidation(String date) throws CsvException.InvalidCsvException {

    if (isValidTimeStamp(date)) {
      return inputDate;
    } else {
      try {
        DateTimeFormatter inputFormatter = DateTimeFormatter
            .ofPattern(DATE_FORMAT, Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter
            .ofPattern(DATE_FORMAT, Locale.ENGLISH);
        LocalDate date1 = LocalDate.parse(date, inputFormatter);
        String formattedDate = outputFormatter.format(date1);
        inputDate = formattedDate;
      } catch (DateTimeParseException e) {
        {
          logger.error("Exception occurred while date format is invalid :{} ", date);
          throw new CsvException.InvalidCsvException("Could not parse date parameter: Invalid Date");
        }
      }

    }
    return inputDate;
  }

  public static boolean isAlphaNumeric(String cookie) throws Exception {
    if (cookie.isEmpty() || cookie == null) {
      logger.error("Exception occurred while cookie is empty:{} ", cookie);
      throw new Exception("ROW_MISSING_ERROR_CODE ROW_MISSING_ERROR_ERROR_MESSAGE");
    }
    Pattern p = Pattern.compile(Constants.REGEX);
    if (cookie == null || p.matcher(cookie).matches() == false) {
      logger.error("Exception occurred while cookie is alphanumeric or not:{} ", cookie);
      throw new Exception("Exception occurred while cookie is alphanumeric or not");
    }
    Matcher m = p.matcher(cookie);
    return m.matches();
  }
}
