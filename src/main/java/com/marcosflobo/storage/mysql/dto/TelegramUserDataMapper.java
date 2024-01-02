package com.marcosflobo.storage.mysql.dto;

import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.api.objects.User;

@Singleton
public class TelegramUserDataMapper {

  public TelegramUserData fromUserToTelegramUserData(User user) {

    TelegramUserData ret = new TelegramUserData();

    ret.setIsBot(user.getIsBot());
    ret.setLanguageCode(user.getLanguageCode());
    ret.setCanJoinGroups(user.getCanJoinGroups());
    ret.setCanReadAllGroupMessages(user.getCanReadAllGroupMessages());
    ret.setSupportInlineQueries(user.getSupportInlineQueries());
    ret.setIsPremium(user.getIsPremium());
    ret.setAddedToAttachmentMenu(user.getAddedToAttachmentMenu());
    return ret;
  }
}
