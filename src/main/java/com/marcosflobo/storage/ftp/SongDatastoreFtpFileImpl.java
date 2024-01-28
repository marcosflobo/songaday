package com.marcosflobo.storage.ftp;

import com.marcosflobo.entity.MapperSongEntity;
import com.marcosflobo.entity.Song;
import com.marcosflobo.storage.SongDatastore;
import jakarta.inject.Singleton;
import java.util.Map;

@Singleton
public class SongDatastoreFtpFileImpl implements SongDatastore {

  private final FtpDatastore ftpDatastore;
  private final MapperSongEntity mapperSongEntity;

  public SongDatastoreFtpFileImpl(FtpDatastore ftpDatastore) {
    this.ftpDatastore = ftpDatastore;
    this.mapperSongEntity = new MapperSongEntity();
  }

  @Override
  public void save(Song song) {

    Map<String, SongFtpDto> songs = ftpDatastore.getSongsFile();
    SongFtpDto songFtpDto = mapperSongEntity.songToSongFtpSong(song);
    songs.put(songFtpDto.getTargetDate(), songFtpDto);
    ftpDatastore.save(songs);
  }

  @Override
  public Song findByTargetDate(String targetDate) {

    Map<String, SongFtpDto> songs = ftpDatastore.getSongsFile();
    if (songs.containsKey(targetDate)) {
      return mapperSongEntity.songFtpDtoToSong(songs.get(targetDate));
    }
    return null;
  }
}
