package com.marcosflobo.sendsong;

import com.marcosflobo.storage.SongMemoryDatabase;
import jakarta.inject.Singleton;

@Singleton
public class SongDispatcherToTelegram {

  private final SongMemoryDatabase songMemoryDatabase;

  public SongDispatcherToTelegram(SongMemoryDatabase songMemoryDatabase) {
    this.songMemoryDatabase = songMemoryDatabase;
  }

  public String getNextSong() {

    return songMemoryDatabase.get("2023/12/21").getUrl();
  }
}
