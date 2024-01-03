package com.marcosflobo.storage.mysql.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Serdeable
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
