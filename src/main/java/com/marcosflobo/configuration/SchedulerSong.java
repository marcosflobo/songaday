package com.marcosflobo.configuration;

import com.marcosflobo.usecases.UseCaseSendDailySong;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class SchedulerSong {

  private final UseCaseSendDailySong useCaseSendDailySong;

  public SchedulerSong(UseCaseSendDailySong useCaseSendDailySong) {
    this.useCaseSendDailySong = useCaseSendDailySong;
  }

  /**
   * Run this method every day at 9 a.m.
   */
  @Scheduled(cron = "${schedule.dailysong}")
  void sendDailySong() {

    log.info("Running sendSongOfTheDayToAllUsers");
    useCaseSendDailySong.sendSongOfTheDayToAllUsers();
    log.info("sendSongOfTheDayToAllUsers finished!");
  }
}
