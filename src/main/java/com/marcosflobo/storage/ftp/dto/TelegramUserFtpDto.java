package com.marcosflobo.storage.ftp.dto;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.annotation.Serdeable;
import java.io.IOException;
import lombok.Data;

@Data
@Serdeable
public class TelegramUserFtpDto {

  private Long id;
  private Boolean isBot;
  private String languageCode;
  private Boolean canJoinGroups;
  private Boolean canReadAllGroupMessages;
  private Boolean supportInlineQueries;
  private Boolean isPremium;
  private Boolean addedToAttachmentMenu;

  @Override
  public String toString() {
    return toJson();
  }

  public String toJson() {
    ObjectMapper objectMapper = ObjectMapper.getDefault();

    try {
      return objectMapper.writeValueAsString(this);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
