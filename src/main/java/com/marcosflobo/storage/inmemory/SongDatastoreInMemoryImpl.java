package com.marcosflobo.storage.inmemory;

import com.marcosflobo.entity.Song;
import com.marcosflobo.entity.TelegramUser;
import com.marcosflobo.storage.SongDatastore;
import com.marcosflobo.storage.SongsCache;
import com.marcosflobo.storage.ftp.FtpDatastore;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named("songDatastoreInMemoryImpl")
@Singleton
public class SongDatastoreInMemoryImpl implements SongDatastore {

  private final SongsCache songsCache;
  private final FtpDatastore ftpDatastore;

  public SongDatastoreInMemoryImpl(SongsCache songsCache, @Named("ftpDatastoreSong") FtpDatastore ftpDatastore) {
    this.songsCache = songsCache;
    this.ftpDatastore = ftpDatastore;
  }

  @Override
  public void save(Song song) {

    songsCache.add(song);
  }

  @Override
  public Song findByTargetDate(String targetDate) {
    return songsCache.get(targetDate);
  }

  @Override
  public List<Song> findAll() {
    List<Song> songs = new ArrayList<>();
    BufferedReader file = ftpDatastore.getFile();
    if (file != null) {
      try {
        for (String songInfoAsCSV = file.readLine();
            songInfoAsCSV != null;
            songInfoAsCSV = file.readLine()) {

          String[] split = songInfoAsCSV.split(",");
          Song song = new Song();
          song.setTargetDate(split[0]);
          song.setUrl(split[1]);
          songs.add(song);
        }
        file.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return songs;
  }
}
