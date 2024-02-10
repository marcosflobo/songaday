package com.marcosflobo.telegrambot;

import com.marcosflobo.sendsong.TelegramLanguageMessages;
import com.marcosflobo.usecases.unsubscribetelegramuser.UseCaseUnSubscribeTelegramUser;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

@Singleton
@Getter
@Setter
@Slf4j
public class TelegramUnsubscribe implements TelegramCommand{

  private User user;
  private final UseCaseUnSubscribeTelegramUser useCaseUnSubscribeTelegramUser;
  private final TelegramLanguageMessages telegramLanguageMessages;

  public TelegramUnsubscribe(UseCaseUnSubscribeTelegramUser useCaseUnSubscribeTelegramUser,
      TelegramLanguageMessages telegramLanguageMessages) {
    this.useCaseUnSubscribeTelegramUser = useCaseUnSubscribeTelegramUser;
    this.telegramLanguageMessages = telegramLanguageMessages;
  }

  @Override
  public void execute() {

    useCaseUnSubscribeTelegramUser.unsubscribe(user);
  }

  @Override
  public String getMessage() {
    return telegramLanguageMessages.userUnSubscribed(user);
  }
}
