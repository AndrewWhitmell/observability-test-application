package com.example.observabilitytestapplication;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.springframework.stereotype.Component;

@Component
public class AppHandler implements ObservationHandler<Observation.Context> {

  @Override
  public void onStart(Observation.Context context) {
    ObservationHandler.super.onStart(context);
  }

  @Override
  public void onStop(Observation.Context context) {
    ObservationHandler.super.onStop(context);
  }

  @Override
  public boolean supportsContext(Observation.Context context) {
    return true;
  }
}
