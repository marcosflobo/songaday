package com.marcosflobo.storage.ftp.song;

import com.marcosflobo.entity.MapperSongEntity;
import com.marcosflobo.entity.Song;
import com.marcosflobo.storage.SongDatastore;
import com.marcosflobo.storage.ftp.FtpStoreClient;
import com.marcosflobo.storage.ftp.dto.SongFtpDto;
import jakarta.inject.Singleton;
import java.util.Map;

@Singleton
public class SongDatastoreFtpFileImpl implements SongDatastore {

  private final FtpStoreClient ftpStoreClient;
  private final MapperSongEntity mapperSongEntity;

  public SongDatastoreFtpFileImpl(FtpStoreClient ftpStoreClient) {
    this.ftpStoreClient = ftpStoreClient;
    this.mapperSongEntity = new MapperSongEntity();
  }

  @Override
  public void save(Song song) {

    Map<String, SongFtpDto> songs = ftpStoreClient.getSongsFile();
    SongFtpDto songFtpDto = mapperSongEntity.songToSongFtpSong(song);
    songs.put(songFtpDto.getTargetDate(), songFtpDto);
    ftpStoreClient.save(songs);
  }

  @Override
  public Song findByTargetDate(String targetDate) {

    Map<String, SongFtpDto> songs = ftpStoreClient.getSongsFile();
    if (songs.containsKey(targetDate)) {
      return mapperSongEntity.songFtpDtoToSong(songs.get(targetDate));
    }
    return null;
  }
}
