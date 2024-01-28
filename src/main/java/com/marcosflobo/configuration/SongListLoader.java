package com.marcosflobo.configuration;

import com.marcosflobo.inputsong.FtpService;
import com.marcosflobo.storage.SongMemoryDatabase;
import com.marcosflobo.entity.Song;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class SongListLoader implements ApplicationEventListener<StartupEvent> {

  private final FtpService ftpService;
  private final SongMemoryDatabase songMemoryDatabase;

  public SongListLoader(final FtpService ftpService, SongMemoryDatabase songMemoryDatabase) {
    this.ftpService = ftpService;
    this.songMemoryDatabase = songMemoryDatabase;
  }

  @Override
  public void onApplicationEvent(StartupEvent event) {

    for (String s : ftpService.getCsv()) {
      String[] split = s.split(",");
      Song song = new Song();
      song.setTargetDate(split[0]);
      song.setUrl(split[1]);

      log.info("Adding song {} to database", song);
      songMemoryDatabase.add(song);
    }

  }

  @Override
  public boolean supports(StartupEvent event) {
    return ApplicationEventListener.super.supports(event);
  }
}
