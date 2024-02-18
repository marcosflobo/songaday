package com.marcosflobo.telegrambot;

import com.marcosflobo.sendsong.SongService;
import com.marcosflobo.sendsong.TelegramLanguageMessages;
import com.marcosflobo.sendsong.exception.NoSongForTodayException;
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
  private final SongService songService;
  private final TelegramLanguageMessages telegramLanguageMessages;
  private final TelegramStartSubscribe startCommand;
  private final TelegramUnsubscribe unsubscribeCommand;
  public Bot(SongService songService,
      TelegramLanguageMessages telegramLanguageMessages,
      TelegramStartSubscribe startCommand,
      TelegramUnsubscribe unsubscribeCommand) {
    this.telegramLanguageMessages = telegramLanguageMessages;
    this.songService = songService;
    this.startCommand = startCommand;
    this.unsubscribeCommand = unsubscribeCommand;
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
      Long userId = user.getId();
      long chatId = update.getMessage().getChatId();
      String messageText = update.getMessage().getText();

      SendMessage message = new SendMessage();
      message.setChatId(chatId);
      if (update.getMessage().isCommand()) {
        if (messageText.equals("/start")){
          log.info("Command /start by user '{}'", userId);
          sendGeneralMessage(userId, telegramLanguageMessages.start(user.getLanguageCode()));
        } else if (messageText.equals("/subscribe")) {
          log.info("Command /subscribe by user '{}'", userId);
          startCommand.setUser(user);
          startCommand.execute();
          sendGeneralMessage(userId, startCommand.getMessage());

          // Send today's song
          try {
            String songUrl = songService.getNextSong();
            sendSong(userId, songUrl);
          } catch (NoSongForTodayException e) {
            log.warn(e.getMessage());
          }
        } else if (messageText.equals("/unsubscribe")) {
          log.info("Command /unsubscribe by user '{}'", userId);
          // send farewell message

          // remove user from database
          unsubscribeCommand.setUser(user);
          unsubscribeCommand.execute();
          sendGeneralMessage(userId, unsubscribeCommand.getMessage());
        }
      } else {
        // Action not supported
        sendGeneralMessage(userId, telegramLanguageMessages.actionNotSupported(user));
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
        .text(telegramLanguageMessages.dailySong() + songUrl)
        .build();    //Message content
    try {
      log.info("Sending song to '{}'...", userId);
      execute(message);                        //Actually sending the message
      log.info("Message sent to '{}'", userId);
    } catch (TelegramApiException e) {      //Any error will be printed here
      log.error("Error sending message to user '{}'. Exception: {}", userId, e.getMessage());
    }
  }

}
