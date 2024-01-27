package com.marcosflobo.usecases;

import com.marcosflobo.storage.dto.Song;
import jakarta.inject.Singleton;

@Singleton
public class MapperSongEntity {

  public Song inputSonToSong(InputSong inputSong) {

    Song song = new Song();
    song.setUrl(inputSong.getUrl());
    song.setTargetDate(inputSong.getTargetDate());
    return song;
  }
}
