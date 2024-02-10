package com.marcosflobo.entity;

import com.marcosflobo.usecases.savesong.InputSong;
import com.marcosflobo.storage.ftp.dto.SongFtpDto;
import jakarta.inject.Singleton;

@Singleton
public class MapperSongEntity {

  public Song inputSongToSong(InputSong inputSong) {

    Song song = new Song();
    song.setUrl(inputSong.getUrl());
    song.setTargetDate(inputSong.getTargetDate());
    return song;
  }

  public SongFtpDto songToSongFtpSong(Song song) {

    SongFtpDto songFtpDto = new SongFtpDto();
    songFtpDto.setTargetDate(song.getTargetDate());
    songFtpDto.setUrl(songFtpDto.getUrl());
    return songFtpDto;
  }

  public Song songFtpDtoToSong(SongFtpDto songFtpDto) {

    Song song = new Song();
    song.setTargetDate(songFtpDto.getTargetDate());
    song.setUrl(songFtpDto.getUrl());
    return song;
  }
}
