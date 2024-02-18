package com.marcosflobo.usecases.loadsongs;

import com.marcosflobo.entity.MapperSongEntity;
import com.marcosflobo.entity.Song;
import com.marcosflobo.storage.SongDatastore;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class UseCaseLoadSongs implements LoadSongs {

  private final SongDatastore songDatastore;
  private final MapperSongEntity mapperSongEntity;

  public UseCaseLoadSongs(@Named("songDatastoreInMemoryImpl") SongDatastore songDatastore) {
    this.songDatastore = songDatastore;
    mapperSongEntity = new MapperSongEntity();
  }

  @Override
  public void loadAll() {

    for (Song s : songDatastore.findAll()) {
      InputSong inputSong = new InputSong();
      inputSong.setUrl(s.getUrl());
      inputSong.setTargetDate(s.getTargetDate());
      Song song = mapperSongEntity.inputSongToSong(inputSong);

      log.info("Loading song {} to memory", inputSong);
      songDatastore.save(song);
    }
  }

}
