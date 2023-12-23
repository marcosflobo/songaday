package com.marcosflobo.sendsong;

import static com.marcosflobo.Utils.getDateToday;

import com.marcosflobo.sendsong.exception.NoSongForTodayException;
import com.marcosflobo.storage.SongMemoryDatabase;
import com.marcosflobo.storage.dto.Song;
import jakarta.inject.Singleton;

@Singleton
public class SongService {

  private final SongMemoryDatabase songMemoryDatabase;

  public SongService(SongMemoryDatabase songMemoryDatabase) {
    this.songMemoryDatabase = songMemoryDatabase;
  }

  public String getNextSong() throws NoSongForTodayException {

    String today = getDateToday();
    Song song = songMemoryDatabase.get(today);
    if (song == null) {
      String exceptionMessage = String.format("There is no song in the list for date %s", today);
      throw new NoSongForTodayException(exceptionMessage);
    }
    return song.getUrl();
  }
}
