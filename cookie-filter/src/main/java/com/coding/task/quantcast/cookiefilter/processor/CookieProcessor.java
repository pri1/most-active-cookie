package com.coding.task.quantcast.cookiefilter.processor;

import com.coding.task.quantcast.cookiefilter.parser.CommandLineInput;

/** Interface for filtering the most active cookies */
public interface CookieProcessor {
  void MostActiveCookies(CommandLineInput commandLineInput) throws Exception;
}
