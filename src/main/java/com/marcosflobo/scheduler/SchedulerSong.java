package com.marcosflobo.scheduler;

import com.marcosflobo.usecases.sendasong.UseCaseSendASong;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class SchedulerSong {

  private final UseCaseSendASong useCaseSendASong;

  public SchedulerSong(UseCaseSendASong useCaseSendASong) {
    this.useCaseSendASong = useCaseSendASong;
  }

  /**
   * Run this method every day at the time indicated in the property
   */
  @Scheduled(cron = "${schedule.dailysong}")
  void sendDailySong() {

    log.info("Running sendSongOfTheDayToAllUsers");
    useCaseSendASong.sendSongOfTheDayToAllUsers();
    log.info("sendSongOfTheDayToAllUsers finished!");
  }
}
