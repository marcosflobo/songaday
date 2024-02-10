package com.marcosflobo.usecases.sendasong;

import com.marcosflobo.sendsong.SongService;
import com.marcosflobo.sendsong.exception.NoSongForTodayException;
import com.marcosflobo.storage.UserDatastore;
import com.marcosflobo.telegrambot.Bot;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class UseCaseSendASong {

  private final SongService songService;
  private final Bot bot;
  private final UserDatastore userDatastore;

  public UseCaseSendASong(SongService songService, Bot bot, UserDatastore userDatastore) {
    this.songService = songService;
    this.bot = bot;
    this.userDatastore = userDatastore;
  }

  public void sendSongOfTheDayToAllUsers() {

    try {
      // Get today's song
      String songUrl = songService.getNextSong();

      // Send song to all users
      userDatastore.findAll().forEach(telegramUser -> bot.sendSong(telegramUser.getId(), songUrl));

    } catch (NoSongForTodayException e) {
      log.warn(e.getMessage());
    }
  }

}
