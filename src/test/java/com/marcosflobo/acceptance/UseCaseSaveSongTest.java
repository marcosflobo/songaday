package com.marcosflobo.acceptance;

import com.marcosflobo.storage.dto.Song;
import com.marcosflobo.usecases.InputSong;
import com.marcosflobo.usecases.SongDatastore;
import com.marcosflobo.usecases.SongDatastoreInMemoryImpl;
import com.marcosflobo.usecases.UseCaseSaveSong;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UseCaseSaveSongTest {

  private static UseCaseSaveSong useCaseSaveSong;
  private static SongDatastore songDatastore;

  @BeforeAll
  static void setUp() {

    songDatastore = new SongDatastoreInMemoryImpl();
    useCaseSaveSong = new UseCaseSaveSong(songDatastore);
  }

  @Test
  void testSaveSong() {

    // GIVEN: A song URL
    InputSong inputSong = new InputSong();
    inputSong.setTargetDate("2024/01/27");
    inputSong.setUrl("http://www.marcosflobo.com");
    Song song = new Song();
    song.setTargetDate(inputSong.getTargetDate());
    song.setUrl(inputSong.getUrl());

    // WHEN: Arriving to the system it's saved
    useCaseSaveSong.setInputSong(inputSong);
    useCaseSaveSong.store();

    // THEN: It's stored
    Song foundSong = songDatastore.find(song);

    Assertions.assertEquals(song.getUrl(), foundSong.getUrl());
  }

}
