package com.coding.task.quantcast.cookiefilter.executor.impl;

import static com.coding.task.quantcast.cookiefilter.parser.CookieLogParser.parseCommandLineInput;

import com.coding.task.quantcast.cookiefilter.constants.ProcessStatus;
import com.coding.task.quantcast.cookiefilter.exception.LogParsingException;
import com.coding.task.quantcast.cookiefilter.executor.ProcessExecutor;
import com.coding.task.quantcast.cookiefilter.parser.CommandLineInput;
import com.coding.task.quantcast.cookiefilter.processor.CookieProcessor;
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
      CommandLineInput commandLineInput = parseCommandLineInput(args);
      cookieProcessor.mostActiveCookies(commandLineInput);
      return ProcessStatus.SUCCESS.getValue();
    } catch (LogParsingException | RuntimeException e) {
      log.error("Program failed! {}", e);
    }
    return ProcessStatus.PROGRAM_FAILED.getValue();
  }
}
