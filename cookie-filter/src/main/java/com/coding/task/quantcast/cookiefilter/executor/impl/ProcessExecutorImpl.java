package com.coding.task.quantcast.cookiefilter.executor.impl;

import static com.coding.task.quantcast.cookiefilter.parser.CookieLogParser.parseCommandLineInput;
import static com.coding.task.quantcast.cookiefilter.utils.DisplayToTerminal.printToTerminal;

import com.coding.task.quantcast.cookiefilter.constants.ProcessStatus;
import com.coding.task.quantcast.cookiefilter.exception.LogParsingException;
import com.coding.task.quantcast.cookiefilter.executor.ProcessExecutor;
import com.coding.task.quantcast.cookiefilter.parser.CommandLineInput;
import com.coding.task.quantcast.cookiefilter.processor.CookieProcessor;
import java.util.Map;
import java.util.OptionalLong;
import lombok.extern.log4j.Log4j2;

/**
 * Implementation for the interface ProcessExecutor
 */
@Log4j2
public class ProcessExecutorImpl implements ProcessExecutor {

  private CookieProcessor cookieProcessor;

  public ProcessExecutorImpl(CookieProcessor cookieProcessor) {
    this.cookieProcessor = cookieProcessor;
  }

  @Override
  public int executeProcess(String[] args) throws Exception {
    try {
      long start = System.currentTimeMillis();
      CommandLineInput commandLineInput = parseCommandLineInput(args);
      Map<String, Long> groupCookieByDate = cookieProcessor.mostActiveCookies(commandLineInput);
      log.info("Calculate the frequency of most active cookies  {}", groupCookieByDate);
      OptionalLong mostActiveCookieFreq = groupCookieByDate.values().stream()
          .mapToLong(count -> count).max();
      mostActiveCookieFreq.ifPresent(maxFreq -> printToTerminal(groupCookieByDate, maxFreq));
      log.info("Total execution time taken in millis to published " + " "
          + "records in groupCookieByDate from cookie log csv file : {}"
          + +(System.currentTimeMillis() - start) / 1000f + " seconds");
      return ProcessStatus.SUCCESS.getValue();
    } catch (LogParsingException | RuntimeException e) {
      log.error("Program failed! {}", e);
    }
    return ProcessStatus.PROGRAM_FAILED.getValue();
  }
}
