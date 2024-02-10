package com.marcosflobo.applicationeventlistener;

import com.marcosflobo.usecases.loadsongs.UseCaseLoadSongs;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class SongsLoaderApplicationEventListener implements ApplicationEventListener<StartupEvent> {

  private final UseCaseLoadSongs useLoadSongs;

  public SongsLoaderApplicationEventListener(final UseCaseLoadSongs useLoadSongs) {
    this.useLoadSongs = useLoadSongs;
  }

  @Override
  public void onApplicationEvent(StartupEvent event) {

    useLoadSongs.loadAll();
  }

  @Override
  public boolean supports(StartupEvent event) {
    return ApplicationEventListener.super.supports(event);
  }
}
