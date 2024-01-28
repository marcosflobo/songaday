package com.marcosflobo.usecases;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marcosflobo.storage.ftp.FtpDatastore;
import com.marcosflobo.storage.ftp.SongFtpDto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FtpDatastoreTest {

  @Test
  void addNewLine() throws IOException {

    // GIVEN: A song to be added to a file
    SongFtpDto songFtpDto = new SongFtpDto();
    songFtpDto.setTargetDate("2100/02/26");
    songFtpDto.setUrl(
        "https://open.spotify.com/intl-es/track/4tKGFmENO69tZR9ahgZu48?si=39cfdb6b8d4143c5");

    FtpDatastore ftpDatastore = new FtpDatastore();

    URL resourceUrl = this.getClass().getClassLoader().getResource("songlist.txt");
    String filePath = new File(resourceUrl.getFile()).getPath();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
    Map<String, SongFtpDto> songFtpDtoMap = new HashMap<>();

    ftpDatastore.buildSongMap(bufferedReader, songFtpDtoMap);

    bufferedReader.close();

    songFtpDtoMap.put(songFtpDto.getTargetDate(), songFtpDto);
    StringBuilder stringBuilder = ftpDatastore.buildSongsListFile(songFtpDtoMap);

    assertTrue(stringBuilder.toString().contains(songFtpDto.getTargetDate()));
    assertTrue(stringBuilder.toString().contains(songFtpDto.getUrl()));
  }
}