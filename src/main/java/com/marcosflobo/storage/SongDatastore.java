package com.marcosflobo.storage;

import com.marcosflobo.entity.Song;
import java.util.List;

public interface SongDatastore {

  void save(Song song);
  Song findByTargetDate(String targetDate);
  List<Song> findAll();
}
