package com.marcosflobo.entity;

import lombok.Data;

@Data
public class TelegramUser {

  private Long id;
  private Boolean isBot;
  private String languageCode;
  private Boolean canJoinGroups;
  private Boolean canReadAllGroupMessages;
  private Boolean supportInlineQueries;
  private Boolean isPremium;
  private Boolean addedToAttachmentMenu;
}
