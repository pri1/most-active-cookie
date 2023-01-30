package com.coding.task.quantcast.cookiefilter.processor.impl;

import static com.coding.task.quantcast.cookiefilter.parser.CookieLogParser.parseLog;

import com.coding.task.quantcast.cookiefilter.parser.CommandLineInput;
import com.coding.task.quantcast.cookiefilter.processor.CookieProcessor;
import com.coding.task.quantcast.cookiefilter.validators.CsvDataValidator;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

/**
 * Implementation for the interface CookieProcessor
 */
@Log4j2
public class CookieProcessorImpl implements CookieProcessor {

  @Override
  public Map<String, Long> mostActiveCookies(CommandLineInput commandLineInput) throws Exception {
    log.info("File path and Date {}", commandLineInput.getFileName(),
        commandLineInput.getSelectedDate());
    CsvDataValidator.validateFileExtension(commandLineInput.getFileName());

    return parseLog(commandLineInput);


  }
}
