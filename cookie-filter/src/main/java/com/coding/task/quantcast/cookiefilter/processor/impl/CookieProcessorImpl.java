package com.coding.task.quantcast.cookiefilter.processor.impl;

import static com.coding.task.quantcast.cookiefilter.parser.CookieLogParser.parseLog;
import static com.coding.task.quantcast.cookiefilter.utils.DisplayToTerminal.printToTerminal;

import com.coding.task.quantcast.cookiefilter.parser.CommandLineInput;
import com.coding.task.quantcast.cookiefilter.processor.CookieProcessor;
import com.coding.task.quantcast.cookiefilter.validators.CsvDataValidator;
import java.util.Map;
import java.util.OptionalLong;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

/**
 * Implementation for the interface CookieProcessor
 */
@Log4j2
public class CookieProcessorImpl implements CookieProcessor {

  @Override
  public void mostActiveCookies(CommandLineInput commandLineInput) throws Exception {
    log.info("File path and Date {}", commandLineInput.getFileName(),
        commandLineInput.getSelectedDate());
    CsvDataValidator.validateFileExtension(commandLineInput.getFileName());
    Map<String, Long> groupCookieByDate = parseLog(commandLineInput);
    OptionalLong mostActiveCookieFreq = mostActiveCookieFreq(groupCookieByDate);
    mostActiveCookieFreq.ifPresent(maxFreq -> printToTerminal(groupCookieByDate, maxFreq));
  }


  /**
   * Return the frequency of most active cookies, which is the max values of all the occurrences
   */
  private OptionalLong mostActiveCookieFreq(Map<String, Long> groupOfCookieByDate) {
    log.info("Calculate the frequency of most active cookies  {}", groupOfCookieByDate);
    return groupOfCookieByDate.values().stream().mapToLong(count -> count).max();
  }
}
