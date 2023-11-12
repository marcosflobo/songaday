package com.marcosflobo.sendsong;

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

  public void sendAudio(String urlAudio) {

    log.info("Sending son URL through Telegram Bot");
    bot.sendAudio(urlAudio);
  }
}
