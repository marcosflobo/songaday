package com.marcosflobo.telegrambot;

import com.marcosflobo.sendsong.SongDispatcherToTelegram;
import com.marcosflobo.sendsong.TelegramBotServiceUtils;
import com.marcosflobo.sendsong.TelegramLanguageMessages;
import com.marcosflobo.storage.UsersService;
import io.micronaut.context.annotation.Property;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Singleton
public class Bot extends TelegramLongPollingBot {

  @Property(name = "telegram.token")
  private String token;

  @Property(name = "telegram.botusername")
  private String botUserName;
  private final TelegramBotServiceUtils telegramBotServiceUtils;
  private final UsersService usersService;
  private final SongDispatcherToTelegram songDispatcherToTelegram;
  private final TelegramLanguageMessages telegramLanguageMessages;

  public Bot(TelegramBotServiceUtils telegramBotServiceUtils, UsersService usersService,
      SongDispatcherToTelegram songDispatcherToTelegram,
      TelegramLanguageMessages telegramLanguageMessages) {
    this.telegramBotServiceUtils = telegramBotServiceUtils;
    this.usersService = usersService;
    this.telegramLanguageMessages = telegramLanguageMessages;
    this.songDispatcherToTelegram = songDispatcherToTelegram;
  }

  @Override
  public String getBotUsername() {
    return botUserName;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      User user = update.getMessage().getFrom();
      String userFirstName = user.getFirstName();
      Long userId = user.getId();
      long chatId = update.getMessage().getChatId();
      String messageText = update.getMessage().getText();

      SendMessage message = new SendMessage();
      message.setChatId(chatId);
      if (update.getMessage().isCommand()) {
        if (messageText.equals("/subscribe")) {
          usersService.add(userId);

          log.info("Users so far: {}", usersService);
          sendGeneralMessage(userId, telegramLanguageMessages.userSubscribed(user));
          String songUrl = songDispatcherToTelegram.getNextSong();
          sendSong(userId, songUrl);
        }
      } else {
        sendGeneralMessage(userId,
            "¡Hola! 👋 " + userFirstName + " Has enviado el siguiente mensaje: " + messageText);
      }
    }
  }

  public void sendGeneralMessage(Long userId, String msg) {
    SendMessage message = SendMessage.builder()
        .chatId(userId) //Who are we sending a message to
        .text(msg)
        .build();    //Message content
    try {
      log.info("Sending message '{}' to '{}'...", msg, userId);
      execute(message);                        //Actually sending the message
      log.info("Message sent to '{}'", userId);
    } catch (TelegramApiException e) {      //Any error will be printed here
      log.error("Error sending message to user '{}'. Exception: {}", userId, e.getMessage());
    }
  }

  public void sendSong(Long userId, String songUrl) {

    SendMessage message = SendMessage.builder()
        .chatId(userId) //Who are we sending a message to
        .text(telegramBotServiceUtils.buildMessageSendSong(songUrl))
        .build();    //Message content
    try {
      log.info("Sending song {} to '{}'...", songUrl, userId);
      execute(message);                        //Actually sending the message
      log.info("Message sent to '{}'", userId);
    } catch (TelegramApiException e) {      //Any error will be printed here
      log.error("Error sending message to user '{}'. Exception: {}", userId, e.getMessage());
    }
  }

}
