package com.example.stock_api.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

  private static final long CONNECT_TIMEOUT_MILLIS = 3000L;
  private static final long READ_TIMEOUT_MILLIS = 3000L;

  @Bean
  public RestTemplate restTemplate(final RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(CONNECT_TIMEOUT_MILLIS))
        .setReadTimeout(Duration.ofMillis(READ_TIMEOUT_MILLIS))
        .build();
  }
}
