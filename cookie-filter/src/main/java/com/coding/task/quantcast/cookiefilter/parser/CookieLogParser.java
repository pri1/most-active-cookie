package com.coding.task.quantcast.cookiefilter.parser;

import static com.coding.task.quantcast.cookiefilter.constants.Constants.COOKIE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.DATE_OPTION;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.DELIMITER;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.EXTRA_LINE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.FILE_LOCATION_OPTION;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.MISSING_CSV_HEADER_OR_INVALID_FORMAT_ERROR_MESSAGE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.TIMESTAMP;
import static java.time.LocalDate.parse;

import com.coding.task.quantcast.cookiefilter.exception.CsvException;
import com.coding.task.quantcast.cookiefilter.exception.CsvException.InvalidCsvException;
import com.coding.task.quantcast.cookiefilter.exception.LogParsingException;
import com.coding.task.quantcast.cookiefilter.validators.CsvDataValidator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import javax.management.InvalidApplicationException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@Log4j2
public class CookieLogParser {

  private static long numberOfLines;
  private static int headersLength;

  private static int lineSize;


  /**
   * Parsing CSV log file content
   */
  public static Map<String, Long> parseLog(CommandLineInput commandLineInput) throws Exception {

    RandomAccessFile raf;
    try {
      raf = new RandomAccessFile(commandLineInput.getFileName(), "r");
    } catch (FileNotFoundException e) {
      log.error("Exception occurred while file could not find: ");
      throw new FileNotFoundException("Could not find file: " + commandLineInput.getFileName());
    }
    raf.seek(0);

    String header = raf.readLine();

    if (header == null || header.isEmpty() || header.isEmpty()){
      log.error("No errors, and file is empty: {}" ,  header);
      throw new InvalidCsvException("File is empty: " + commandLineInput.getFileName());
    }

    String [] validateHeader = header.split(DELIMITER);

    if (!(validateHeader[0].equals(COOKIE) && validateHeader[1].equals(TIMESTAMP))) {
      log.error( "The csv file has no headers or incorrect format, please ensure it has the correct format");
      throw new InvalidApplicationException(MISSING_CSV_HEADER_OR_INVALID_FORMAT_ERROR_MESSAGE);
    }

    headersLength = header.length();

    if (header != null && headersLength != 0) {
      headersLength = header.length() + EXTRA_LINE;
    }
    raf.seek(headersLength);

    String line = raf.readLine();

    if (line == null || line.isEmpty() || line.isEmpty()){
      log.error("No errors, and file Records are empty: {}" ,  header);
      throw new InvalidCsvException("File Records are empty: " + commandLineInput.getFileName());
    }

    lineSize = line.length();
    if (line != null && lineSize != 0) {
      lineSize = lineSize + EXTRA_LINE;
      numberOfLines = ((raf.length() - headersLength) / lineSize) + 1;
    }

    long res = 0;
    byte[] lineBuffer = new byte[lineSize];
    long bottom = 1;
    long top = numberOfLines;
    long middle;
    while (bottom <= top) {
      middle = bottom + (top - bottom) / 2;
      raf.seek(headersLength + ((middle - 1) * lineSize));
      raf.read(lineBuffer);
      line = new String(lineBuffer);

      String records[] = line.split(DELIMITER);

      String date = CsvDataValidator.dateValidation(
          records[1].substring(0, records[1].length() - 2));

      int comparison = date.compareTo(commandLineInput.getSelectedDate().toString());
      if (comparison == 0) {
        res = middle + 1;
        top = middle - 1;
      } else if (comparison < 0) {
        top = middle - 1;
      } else {
        bottom = middle + 1;
      }
    }
    Map<String, Long> map = new HashMap<>();
    //if (res > 1) {
      raf.seek(headersLength + ((res == 0 ? 0 : res - 1) * lineSize));
      raf.read(lineBuffer);
      line = new String(lineBuffer);

      String arr[] = line.split(",");

      String date;
      try {
        date = CsvDataValidator.dateValidation(arr[1].substring(0, arr[1].length() - 2));
      } catch (CsvException.InvalidCsvException e) {
        throw new RuntimeException(e);
      }

      try {
        map = readFromFile(raf, (int) res, 44, commandLineInput.getSelectedDate().toString(), date);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
   // }

    if (map.isEmpty() || map.size() == 0) {
      log.error("csv log file is empty or Input does not exist in csv file  {}", map);
      throw new InvalidApplicationException("csv file is empty file or date is invalid");
    }

    raf.close();

    return map;
  }

  /**
   * Parsing command line input
   */
  public static CommandLineInput parseCommandLineInput(String[] args) throws LogParsingException {

    CommandLineParser commandLineParser = new DefaultParser();
    Options options = parseCommandOption();

    try {
      CommandLine commandLine = commandLineParser.parse(options, args);
      return new CommandLineInput(commandLine.getOptionValue("file"),
          parse(commandLine.getOptionValue("date")));
    } catch (ParseException e) {
      log.error(
          "A filepath argument is required. Use -f flag with filename and -d flag with date{}",
          e.getMessage());
      outputCommandHelp(options);
      throw new LogParsingException(e);
    }
  }

  /**
   * Parsing command line options (file name and selected date)
   */
  public static Options parseCommandOption() {
    Options commandOptions = new Options();

    Option fileName = new Option(FILE_LOCATION_OPTION, "file", true, "The path of cookie log file");
    fileName.setRequired(true);
    commandOptions.addOption(fileName);

    Option selectedDate =
        new Option(DATE_OPTION, "date", true, "The specific date to get most active cookie");
    selectedDate.setRequired(true);
    commandOptions.addOption(selectedDate);
    return commandOptions;
  }

  /**
   * Help message for command line options
   */
  private static void outputCommandHelp(Options options) {
    HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp("Cookie Log Filter", options);
  }

  private static Map<String, Long> readFromFile(RandomAccessFile randomAccessFile, int position,
      int size,
      String searchValue, String date) throws Exception {

    Map<String, Long> processRowsInMap = new HashMap<>();

    while (date.trim().equals(searchValue)) {

      randomAccessFile.seek(headersLength + ((position - 2) * size));
      byte[] bytes = new byte[lineSize];
      try {
        randomAccessFile.read(bytes);
      } catch (IOException e) {
        log.error("ERROR: Could not read " + randomAccessFile);
      }
      String line = new String(bytes);
      String[] record = line.split(DELIMITER);
      CsvDataValidator.isAlphaNumeric(record[0]);
      date = CsvDataValidator.dateValidation(record[1].substring(0, record[1].length() - 2));
      if (!date.equals(searchValue)) {
        break;
      }

      processRowsInMap.put(record[0], processRowsInMap.getOrDefault(record[0], 0L) + 1);
      if (position == numberOfLines + 1) {
        break;
      }
      position++;
    }
    return processRowsInMap;
  }

}
