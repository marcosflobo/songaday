package com.marcosflobo.inputschedule;

import com.marcosflobo.sendsong.SongService;
import com.marcosflobo.sendsong.exception.NoSongForTodayException;
import com.marcosflobo.storage.RedisServiceUsers;
import com.marcosflobo.storage.mysql.UserMysqlRepository;
import com.marcosflobo.telegrambot.Bot;
import jakarta.inject.Singleton;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class UseCaseSendDailySong {

  private final SongService songService;
  private final RedisServiceUsers redisServiceUsers;
  private final UserMysqlRepository userMysqlRepository;
  private final Bot bot;

  public UseCaseSendDailySong(SongService songService, RedisServiceUsers redisServiceUsers,
      UserMysqlRepository userMysqlRepository, Bot bot) {
    this.songService = songService;
    this.redisServiceUsers = redisServiceUsers;
    this.userMysqlRepository = userMysqlRepository;
    this.bot = bot;
  }

  public void sendSongOfTheDayToAllUsers() {

    try {
      // Get today's song
      String songUrl = songService.getNextSong();

      // Get all users
      List<String> users = redisServiceUsers.getAll();
      if (users.isEmpty()) {
        log.warn("The Redis database is empty");
      } else {
        // Send the song to everyone
        users.forEach(userId -> bot.sendSong(Long.parseLong(userId), songUrl));
      }

      userMysqlRepository.findAll()
          .forEach(telegramUser -> bot.sendSong(telegramUser, songUrl));


    } catch (NoSongForTodayException e) {
      log.warn(e.getMessage());
    }
  }

}
