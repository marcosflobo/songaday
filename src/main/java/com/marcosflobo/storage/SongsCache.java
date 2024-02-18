package com.marcosflobo.storage;

import com.marcosflobo.entity.Song;
import jakarta.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

@Singleton
@Getter
@Setter
public class SongsCache {

  private ConcurrentHashMap<String, Song> memory;

  public SongsCache() {
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
