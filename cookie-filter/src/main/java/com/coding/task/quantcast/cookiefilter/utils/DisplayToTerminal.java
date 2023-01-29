package com.coding.task.quantcast.cookiefilter.utils;

import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DisplayToTerminal {

  /**
   * Output the most active cookies to the terminal
   */
  public static void printToTerminal(Map<String, Long> groupOfCookieByDate,
      long mostActiveCookieFreq) {
    log.info(
        "Scan through the list of cookies and output the ones which have the max frequency value  {}",
        mostActiveCookieFreq, groupOfCookieByDate);
    groupOfCookieByDate.entrySet().stream()
        .filter(x -> x.getValue().equals(mostActiveCookieFreq))
        .map(Map.Entry::getKey)
        .forEach(System.out::println);
  }

}
