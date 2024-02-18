package com.marcosflobo.usecases.subcribetelegramuser;

import com.marcosflobo.entity.TelegramUser;
import com.marcosflobo.usecases.TelegramUserDataMapper;
import com.marcosflobo.storage.UserDatastore;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Singleton
public class UseCaseSubscribeTelegramUser implements SubscribeUser {

  private final UserDatastore userDatastore;
  private final TelegramUserDataMapper telegramUserDataMapper;

  public UseCaseSubscribeTelegramUser(UserDatastore userDatastore) {
    this.userDatastore = userDatastore;
    telegramUserDataMapper = new TelegramUserDataMapper();
  }

  @Override
  public void subscribe(User user) {

    Long userId = user.getId();
    TelegramUser userFound = userDatastore.findById(userId);
    if (userFound == null) {
      log.info("User to be saved {}", userId);
      TelegramUser telegramUser = telegramUserDataMapper.fromUserToTelegramUser(user);
      telegramUser.setId(user.getId());
      userDatastore.save(telegramUser);
      log.info("New user with id '{}' added to the store", userId);
    } else {
      log.info("User with id '{}' already present in the database. Not saving", userId);
    }
  }
}
