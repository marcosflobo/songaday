package com.marcosflobo.usecases;

import com.marcosflobo.entity.TelegramUser;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.api.objects.User;

@Singleton
public class TelegramUserDataMapper {

  public TelegramUser fromUserToTelegramUser(User user) {

    TelegramUser telegramUser = new TelegramUser();
    telegramUser.setId(user.getId());
    telegramUser.setIsBot(user.getIsBot());
    telegramUser.setLanguageCode(user.getLanguageCode());
    telegramUser.setCanJoinGroups(user.getCanJoinGroups());
    telegramUser.setCanReadAllGroupMessages(user.getCanReadAllGroupMessages());
    telegramUser.setSupportInlineQueries(user.getSupportInlineQueries());
    telegramUser.setIsPremium(user.getIsPremium());
    telegramUser.setAddedToAttachmentMenu(user.getAddedToAttachmentMenu());
    return telegramUser;
  }
}
