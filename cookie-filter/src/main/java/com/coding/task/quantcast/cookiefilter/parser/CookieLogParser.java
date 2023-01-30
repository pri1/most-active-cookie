package com.coding.task.quantcast.cookiefilter.parser;

import static com.coding.task.quantcast.cookiefilter.constants.Constants.COOKIE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.DATE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.DATE_OPTION;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.DELIMITER;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.FILE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.FILE_LOCATION_OPTION;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.MISSING_CSV_HEADER_OR_INVALID_FORMAT_ERROR_MESSAGE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.POS_ONE;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.POS_ZERO;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.TIMESTAMP;
import static java.time.LocalDate.parse;

import com.coding.task.quantcast.cookiefilter.exception.LogParsingException;
import com.coding.task.quantcast.cookiefilter.validators.CsvDataValidator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

  /**
   * Parsing CSV log file content
   */
  public static Map<String, Long> parseLog(CommandLineInput commandLineInput)
      throws Exception {

     boolean flag = false;
     String line;

    String csvFile = commandLineInput.getFileName();
    String input = commandLineInput.getSelectedDate().toString();
    Map<String, Long> processRowsInMap = new HashMap<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(csvFile));
      String header[] = br.readLine().split(DELIMITER);

      if (!(header[POS_ZERO].equals(COOKIE) && header[POS_ONE].equals(TIMESTAMP))) {
        throw new InvalidApplicationException(MISSING_CSV_HEADER_OR_INVALID_FORMAT_ERROR_MESSAGE);
      }
      while ((line = br.readLine()) != null) {
        String[] records = line.split(DELIMITER);
        String record = records[POS_ZERO];
        CsvDataValidator.isAlphaNumeric(record);
        String date = CsvDataValidator.dateValidation(records[1]);
        if (!date.equals(input)) {
          if (flag) {
            break;
          }
          continue;
        } else {
          flag = true;
        }

        if (!processRowsInMap.containsKey(record)) {
          processRowsInMap.put(record, 1L);
        } else {
          processRowsInMap.put(record, processRowsInMap.getOrDefault(record, 0L) + 1);
        }

      }

    } catch (FileNotFoundException e) {
      log.error("Exception occurred while file could not find: ");
      throw new FileNotFoundException("Could not find file: " + csvFile);
    } catch (IOException e) {
      log.error("ERROR: Could not read " + csvFile);
    }

    if (processRowsInMap.isEmpty() || processRowsInMap.size() == 0) {
      log.error("csv file is empty or Input does not exist in csv file  {}", input);

    }

    return processRowsInMap;
  }

  /**
   * Parsing command line input
   */
  public static CommandLineInput parseCommandLineInput(String[] args) throws LogParsingException {

    CommandLineParser commandLineParser = new DefaultParser();
    Options options = parseCommandOption();

    try {
      CommandLine commandLine = commandLineParser.parse(options, args);
      return new CommandLineInput(commandLine.getOptionValue(FILE),
          parse(commandLine.getOptionValue(DATE)));
    } catch (ParseException e) {
      log.error(
          "A filepath argument is required. Use -f flag with filename and -d flag with date{}",
          e.getMessage());
      outputCommandHelp(options);
      throw new LogParsingException(e, e.getMessage());
    }
  }

  /**
   * Parsing command line options (file name and selected date)
   */
  public static Options parseCommandOption() {
    Options commandOptions = new Options();

    Option fileName = new Option(FILE_LOCATION_OPTION, FILE, true, "The path of cookie log file");
    fileName.setRequired(true);
    commandOptions.addOption(fileName);

    Option selectedDate =
        new Option(DATE_OPTION, DATE, true, "The specific date to get most active cookie");
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

}
