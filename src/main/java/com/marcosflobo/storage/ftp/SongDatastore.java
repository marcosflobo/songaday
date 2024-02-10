package com.marcosflobo.storage.ftp;

import com.marcosflobo.entity.Song;

public interface SongDatastore {

  void save(Song song);
  Song findByTargetDate(String targetDate);
}
