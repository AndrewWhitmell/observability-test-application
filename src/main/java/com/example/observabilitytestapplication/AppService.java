package com.example.observabilitytestapplication;

import io.micrometer.common.KeyValues;
import io.micrometer.observation.*;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
public class AppService {

  public static final String OBSERVATION_NAME = "observation.name";
  public static final String CONTEXTUAL_NAME = "contextual.name";
  private final Random random = new Random();
  private ObservationRegistry registry;

  public AppService(ObservationRegistry observationRegistry) {
    ObservationFilter filter = context -> context;
    this.registry = observationRegistry;
    registry.observationConfig().observationHandler(new ObservationHandler.AllMatchingCompositeObservationHandler());
    registry.observationConfig().observationFilter(filter);
  }

  public Mono<ServerResponse> handle(ServerRequest request) {
    int number = random.nextInt(3) + 1;
    System.out.println(LocalDateTime.now() + " - Request");
    generateObservation(number);
    return ServerResponse.ok().bodyValue(BodyInserters.fromValue("Ok"));
  }

  public void generateObservation(int number) {
    Observation observation = Observation.start(OBSERVATION_NAME, () -> getContext(number), registry);

    try {
      observation.openScope();
      observation.event(Observation.Event.of("ObservationEvent", CONTEXTUAL_NAME));
    } catch (Exception e) {
      observation.error(e);
      throw e;
    } finally {
      observation.stop();
    }
  }

  public Observation.Context getContext(int number) {
    Observation.Context context = new Observation.Context();
    context.setName(OBSERVATION_NAME);
    context.setContextualName(CONTEXTUAL_NAME);
    context.addLowCardinalityKeyValues(context.getAllKeyValues());
    context.addLowCardinalityKeyValues(KeyValues.of("RandomNumber", String.valueOf(number)));
    return context;
  }
}
