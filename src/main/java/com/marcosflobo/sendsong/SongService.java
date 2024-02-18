package com.marcosflobo.sendsong;

import static com.marcosflobo.Utils.getDateToday;

import com.marcosflobo.entity.Song;
import com.marcosflobo.sendsong.exception.NoSongForTodayException;
import com.marcosflobo.storage.SongDatastore;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class SongService {

  private final SongDatastore songDatastore;

  public SongService(@Named("songDatastoreInMemoryImpl") final SongDatastore songDatastore) {
    this.songDatastore = songDatastore;
  }

  public String getNextSong() throws NoSongForTodayException {

    String today = getDateToday();
    Song song = songDatastore.findByTargetDate(today);
    if (song == null) {
      String exceptionMessage = String.format("There is no song in the list for date %s", today);
      throw new NoSongForTodayException(exceptionMessage);
    }
    return song.getUrl();
  }
}
