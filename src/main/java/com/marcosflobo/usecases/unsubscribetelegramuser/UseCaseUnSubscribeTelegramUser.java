package com.marcosflobo.usecases.unsubscribetelegramuser;

import com.marcosflobo.usecases.TelegramUserDataMapper;
import com.marcosflobo.storage.UserDatastore;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Singleton
public class UseCaseUnSubscribeTelegramUser implements UnSubscribeUser{

  private final UserDatastore userDatastore;
  private final TelegramUserDataMapper telegramUserDataMapper;

  public UseCaseUnSubscribeTelegramUser(UserDatastore userDatastore,
      TelegramUserDataMapper telegramUserDataMapper) {
    this.userDatastore = userDatastore;
    this.telegramUserDataMapper = telegramUserDataMapper;
  }

  @Override
  public void unsubscribe(User user) {

    userDatastore.delete(telegramUserDataMapper.fromUserToTelegramUser(user));
  }
}
