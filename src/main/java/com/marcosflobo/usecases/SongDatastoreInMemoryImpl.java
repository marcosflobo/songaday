package com.marcosflobo.usecases;

import com.marcosflobo.storage.dto.Song;
import jakarta.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SongDatastoreInMemoryImpl implements SongDatastore{

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
