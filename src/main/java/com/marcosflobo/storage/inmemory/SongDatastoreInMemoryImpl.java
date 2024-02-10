package com.marcosflobo.storage.inmemory;

import com.marcosflobo.entity.Song;
import com.marcosflobo.storage.ftp.SongDatastore;
import jakarta.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SongDatastoreInMemoryImpl implements SongDatastore {

  private final ConcurrentHashMap<String, Song> store;

  public SongDatastoreInMemoryImpl() {
    store = new ConcurrentHashMap<>();
  }

  @Override
  public void save(Song song) {

    store.put(song.getTargetDate(), song);
  }

  @Override
  public Song findByTargetDate(String targetDate) {
    return store.get(targetDate);
  }
}
