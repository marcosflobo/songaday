package com.marcosflobo.storage.mysql.dto;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
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
  @TypeDef(type = DataType.JSON)
  private TelegramUserData telegramUserData;
}
