package com.marcosflobo.configuration;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class SchedulerPing {

  @Scheduled(fixedDelay = "120s")
  public void ping() {
    log.info("🕙Service alive");
  }
}
