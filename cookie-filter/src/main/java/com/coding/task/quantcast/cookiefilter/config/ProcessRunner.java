package com.coding.task.quantcast.cookiefilter.config;

import static java.lang.System.exit;

import com.coding.task.quantcast.cookiefilter.executor.ProcessExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@Log4j2
public class ProcessRunner implements CommandLineRunner {
  private ApplicationContext applicationContext;
  private ProcessExecutor processExecutor;

  public ProcessRunner(ApplicationContext applicationContext, ProcessExecutor processExecutor) {
    this.applicationContext = applicationContext;
    this.processExecutor = processExecutor;
  }

  @Override
  public void run(String[] args) {
    log.info("Program started!");
    terminate(() -> {
      try {
        return processExecutor.executeProcess(args);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void terminate(ExitCodeGenerator exitCodeGenerator) {
    exit(SpringApplication.exit(applicationContext, exitCodeGenerator));
  }
}
