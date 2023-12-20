package com.marcosflobo.storage;

import com.marcosflobo.storage.dto.Song;
import jakarta.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

@Singleton
@Getter
@Setter
public class SongMemoryDatabase {

  private ConcurrentHashMap<String, Song> memory;

  public SongMemoryDatabase() {
    this.memory = new ConcurrentHashMap<>();
  }

  public Song add(Song song) {
    memory.put(song.getTargetDate(), song);
    return song;
  }

  public Song get(String k) {
    return memory.get(k);
  }

}
