package com.example.observabilitytestapplication;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppHandler implements ObservationHandler<Observation.Context> {

  @Override
  public void onStart(Observation.Context context) {
    ObservationHandler.super.onStart(context);
  }

  @Override
  public void onStop(Observation.Context context) {
    log.info("Observation made - {}", context.getName());
    ObservationHandler.super.onStop(context);
  }

  @Override
  public boolean supportsContext(Observation.Context context) {
    return true;
  }
}
