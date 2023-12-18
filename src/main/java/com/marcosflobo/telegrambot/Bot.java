package com.marcosflobo.telegrambot;

import com.marcosflobo.sendsong.TelegramBotServiceUtils;
import com.marcosflobo.storage.SongReader;
import com.marcosflobo.storage.UsersService;
import io.micronaut.context.annotation.Property;
import java.io.IOException;
import java.util.List;
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

  private Long userId;

  private final TelegramBotServiceUtils telegramBotServiceUtils;
  private final UsersService usersService;
  private final SongReader songReader;

  public Bot(TelegramBotServiceUtils telegramBotServiceUtils, UsersService usersService,
      SongReader songReader) {
    this.telegramBotServiceUtils = telegramBotServiceUtils;
    this.usersService = usersService;
    this.songReader = songReader;
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
      userId = user.getId();
      long chatId = update.getMessage().getChatId();
      String messageText = update.getMessage().getText();

      SendMessage message = new SendMessage();
      message.setChatId(chatId);
      if (update.getMessage().isCommand()) {
        if (messageText.equals("/subscribe")) {
          usersService.add(userId);

          log.info("Users so far: {}", usersService);
          message.setText("Yeah!💪 You are subscribed now!");
          try {
            List<String> tracks = songReader.readTracksFromFile();
            send(tracks.get(0));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      } else {
        message.setText(
            "¡Hola! 👋 " + userFirstName + " Has enviado el siguiente mensaje: " + messageText);
        try {
          execute(message);
        } catch (TelegramApiException e) {
          log.error("Error at the time to send a message on onUpdateReceived. "
                  + "Update object: {} - "
                  + "Exception: {}",
              update,
              e.getMessage());
        }
      }
    }
  }

  public void send(String what) {

    SendMessage message = SendMessage.builder()
        .chatId(userId) //Who are we sending a message to
        .text(telegramBotServiceUtils.buildMessageSendSong(what))
        .build();    //Message content
    try {
      log.debug("Sending message to '{}'...", userId);
      execute(message);                        //Actually sending the message
      log.info("Message sent to '{}'", userId);
    } catch (TelegramApiException e) {      //Any error will be printed here
      log.error("Error sending message to user '{}'. Exception: {}", userId, e.getMessage());
    }
  }

}
