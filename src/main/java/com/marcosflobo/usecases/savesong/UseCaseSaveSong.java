package com.marcosflobo.usecases.savesong;

import com.marcosflobo.entity.MapperSongEntity;
import com.marcosflobo.entity.Song;
import com.marcosflobo.storage.SongDatastore;
import jakarta.inject.Singleton;
import lombok.Setter;

@Singleton
public class UseCaseSaveSong implements SaveSong {

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

    Song song = mapperSongEntity.inputSongToSong(inputSong);
    songDatastore.save(song);
  }

}
