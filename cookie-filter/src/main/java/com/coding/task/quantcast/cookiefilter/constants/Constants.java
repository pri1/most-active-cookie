package com.coding.task.quantcast.cookiefilter.constants;

public final class Constants {

  public static final String MISSING_CSV_HEADER_OR_INVALID_FORMAT_ERROR_MESSAGE =
      "The csv file has no headers or incorrect format, please ensure it has the correct upload format";

  public static final String CSV_FILE_EXTENSION_ERROR_ERROR_MESSAGE =
      "Extension should be only Csv file allowed";
  public static final String FILE_EXTENSION_PATTERN =
      "([^\\.]+(\\.(?i)(csv))$)";
  public static final String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$";
  public static final String COOKIE = "cookie";
  public static final String TIMESTAMP = "timestamp";
  public static final String DATE_REGEX = "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
  public static String INPUT_FILE_PATH = "src/main/resources/cookie_log.csv";

  public static String INVALID_INPUT_FILE_PATH = "src/main/resources/dummy.csv";

  public static final String DATE_FORMAT = "yyyy-MM-dd";

  public static final String DATE_TIME_FORMAT= "yyyy-MM-dd'T'HH:mm:ss+ss:ss";

  public static final String FILE = "file";
  public static final String DATE = "date";

  public static final String FILE_LOCATION_OPTION = "f";
  public static final String DATE_OPTION = "d";

  public static final String DELIMITER = ",";

  public static final int POS_ZERO = 0;
  public static final int POS_ONE = 1;


  private Constants() {
  }

}