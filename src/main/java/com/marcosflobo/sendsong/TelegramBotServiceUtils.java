package com.marcosflobo.sendsong;

import javax.inject.Singleton;

@Singleton
public class TelegramBotServiceUtils {

  public String buildMessageSendSong(String url) {

    return "\uD83C\uDFB5\uD83D\uDC9CDaily song arrived!\uD83D\uDC47\uD83C\uDFFD\r\n\r\n" + url;
  }

}
