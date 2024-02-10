package com.marcosflobo.telegrambot;

import com.marcosflobo.sendsong.TelegramLanguageMessages;
import com.marcosflobo.usecases.subcribetelegramuser.UseCaseSubscribeTelegramUser;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

@Singleton
@Setter
@Getter
@Slf4j
public class TelegramStartSubscribe implements TelegramCommand {

  private User user;

  private final UseCaseSubscribeTelegramUser useCaseSubscribeTelegramUser;
  private final TelegramLanguageMessages telegramLanguageMessages;

  public TelegramStartSubscribe(UseCaseSubscribeTelegramUser useCaseSubscribeTelegramUser,
      TelegramLanguageMessages telegramLanguageMessages) {
    this.useCaseSubscribeTelegramUser = useCaseSubscribeTelegramUser;
    this.telegramLanguageMessages = telegramLanguageMessages;
  }

  @Override
  public void execute() {

    useCaseSubscribeTelegramUser.subscribe(user);
  }

  @Override
  public String getMessage() {
    return telegramLanguageMessages.userSubscribed(user);
  }
}
