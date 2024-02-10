package com.marcosflobo.storage;

import com.marcosflobo.entity.Song;

public interface SongDatastore {

  void save(Song song);
  Song findByTargetDate(String targetDate);
}
