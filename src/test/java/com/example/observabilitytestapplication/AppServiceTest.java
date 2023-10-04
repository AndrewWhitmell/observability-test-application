package com.example.observabilitytestapplication;

import io.micrometer.observation.tck.TestObservationRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.observabilitytestapplication.AppService.OBSERVATION_NAME;
import static io.micrometer.observation.tck.TestObservationRegistryAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureObservability
class AppServiceTest {

  @Autowired TestObservationRegistry registry;
  @Autowired AppService service;

  @TestConfiguration
  static class ObservationTestConfiguration {
    @Bean
    @Primary
    TestObservationRegistry testObservationRegistry() {
      return TestObservationRegistry.create();
    }
  }

  @Test
  void testObservationProduced() {
    service.generateObservation(2);

    assertThat(registry)
        .hasObservationWithNameEqualTo(OBSERVATION_NAME)
        .that()
        .hasLowCardinalityKeyValue("RandomNumber", "2");
  }

}
