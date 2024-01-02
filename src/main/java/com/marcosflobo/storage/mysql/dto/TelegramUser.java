package com.marcosflobo.storage.mysql.dto;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Serdeable
@MappedEntity
@Data
public class TelegramUser {

  @Id
  @GeneratedValue(GeneratedValue.Type.AUTO)
  private Long id;
  private Long telegramUserId;
  private String telegramUserData;
}
