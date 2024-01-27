package com.marcosflobo.usecases;

import com.marcosflobo.storage.dto.Song;
import jakarta.inject.Singleton;
import lombok.Setter;

@Singleton
public class UseCaseSaveSong implements StoreSong {

  @Setter
  private InputSong inputSong;

  private final SongDatastore songDatastore;
  private final MapperSongEntity mapperSongEntity;

  public UseCaseSaveSong(SongDatastore songDatastore) {
    this.songDatastore = songDatastore;
    mapperSongEntity = new MapperSongEntity();
  }

  @Override
  public void store() {

    Song song = mapperSongEntity.inputSonToSong(inputSong);
    songDatastore.save(song);
  }

}
