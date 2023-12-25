package com.marcosflobo.inputsong;

import com.marcosflobo.telegrambot.Bot;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class TelegramBotService {

  private final Bot bot;

  public TelegramBotService(Bot bot) {
    this.bot = bot;
  }

  public void sendMessage(String url) {

    log.info("Sending song URL through Telegram Bot");
    //bot.sendSong(1L, url);
  }
}
