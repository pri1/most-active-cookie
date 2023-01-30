package com.coding.task.quantcast.cookiefilter;

import static com.coding.task.quantcast.cookiefilter.constants.Constants.INPUT_FILE_PATH;
import static com.coding.task.quantcast.cookiefilter.constants.Constants.INVALID_INPUT_FILE_PATH;
import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.coding.task.quantcast.cookiefilter.parser.CommandLineInput;
import com.coding.task.quantcast.cookiefilter.processor.CookieProcessor;
import com.coding.task.quantcast.cookiefilter.processor.impl.CookieProcessorImpl;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CookieProcessorApplicationTests {

  private CookieProcessor cookieFilter;
  private PrintStream stdout = System.out;
  private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    cookieFilter = new CookieProcessorImpl();
    PrintStream output = new PrintStream(byteArrayOutputStream);
    System.setOut(output);
  }

  @AfterEach
  public void tearDown() {
    System.setOut(stdout);
  }

  /**
   * When file path and date are valid, output the most active cookie strings
   */
  @Test
  public void filterMostActiveCookies_ValidFilePathAndDate_OutputCookieStrings()
      throws Exception {
    CommandLineInput commandLineInput = new CommandLineInput(INPUT_FILE_PATH, parse("2018-12-09"));
    cookieFilter.mostActiveCookies(commandLineInput);
    assertThat(byteArrayOutputStream.toString().contains("AtY0laUfhglK3lC7"));
    assertThat(!byteArrayOutputStream.toString().contains("SAZuXPGUrfbcn5UA"));
    assertThat(!byteArrayOutputStream.toString().contains("5UAVanZf6UtGyKVS"));
    assertThat(!byteArrayOutputStream.toString().contains("AtY0laUfhglK3lC7"));
  }

  /**
   * When a specific date has more than one most active cookies, output all of them
   */
  @Test
  public void filterMostActiveCookies_HasMoreThanOneMostActiveCookies_OutputAllOfThem()
      throws Exception {
    CommandLineInput commandLineInput = new CommandLineInput(INPUT_FILE_PATH, parse("2018-12-06"));
    cookieFilter.mostActiveCookies(commandLineInput);
    assertThat(byteArrayOutputStream.toString().contains("8xYHIASHaBa79xzf"));
    assertThat(byteArrayOutputStream.toString().contains("1dSLJdsaDJLDsdSd"));
    assertThat(!byteArrayOutputStream.toString().contains("A8SADNasdNadBBda"));
  }

  /**
   * When file path is invalid, throw exception
   */
  @Test
  public void filterMostActiveCookies_InvalidFilePath_ThrowException() {
    CommandLineInput commandLineInput = new CommandLineInput(INVALID_INPUT_FILE_PATH,
        parse("2018-12-09"));
    assertThatThrownBy(() -> cookieFilter.mostActiveCookies(commandLineInput))
        .isInstanceOf(FileNotFoundException.class);
  }

  /**
   * When date is not exist, output is empty (no cookie)
   */
  @Test
  public void filterMostActiveCookies_InvalidDate_ThrowException() throws Exception {
    CommandLineInput commandInput = new CommandLineInput(INPUT_FILE_PATH, parse("2021-12-09"));
    cookieFilter.mostActiveCookies(commandInput);
    assertThat(byteArrayOutputStream.toString().isEmpty());
  }
}
