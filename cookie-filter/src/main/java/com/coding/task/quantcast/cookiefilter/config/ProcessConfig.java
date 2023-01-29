package com.coding.task.quantcast.cookiefilter.config;

import com.coding.task.quantcast.cookiefilter.executor.ProcessExecutor;
import com.coding.task.quantcast.cookiefilter.executor.impl.ProcessExecutorImpl;
import com.coding.task.quantcast.cookiefilter.processor.CookieProcessor;
import com.coding.task.quantcast.cookiefilter.processor.impl.CookieProcessorImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {

  @Bean
  public CommandLineRunner commandLineRunner(
      ApplicationContext applicationContext, ProcessExecutor processExecutor) {
    return new ProcessRunner(applicationContext, processExecutor);
  }

  @Bean
  public CookieProcessor cookieFilter() {
    return new CookieProcessorImpl();
  }

  @Bean
  public ProcessExecutor processExecutor(CookieProcessor cookieProcessor) {
    return new ProcessExecutorImpl(cookieProcessor);
  }
}
