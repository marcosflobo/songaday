package com.marcosflobo.usecases;

import com.marcosflobo.storage.dto.Song;

public interface SongDatastore {

  void save(Song song);
  Song find(Song song);
}
