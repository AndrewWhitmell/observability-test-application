package com.example.observabilitytestapplication;

import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ObservabilityTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(ObservabilityTestApplication.class, args);
  }

  @Bean
  ObservationRegistry observationRegistry() {
    return ObservationRegistry.create();
  }
}
