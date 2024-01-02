package com.marcosflobo.storage.mysql.dto;

import lombok.Data;

@Data
public class TelegramUserData {

  private Boolean isBot;
  private String languageCode;
  private Boolean canJoinGroups;
  private Boolean canReadAllGroupMessages;
  private Boolean supportInlineQueries;
  private Boolean isPremium;
  private Boolean addedToAttachmentMenu;
}
