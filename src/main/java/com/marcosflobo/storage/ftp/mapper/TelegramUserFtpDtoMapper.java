package com.marcosflobo.storage.ftp.mapper;

import com.marcosflobo.entity.TelegramUser;
import com.marcosflobo.storage.ftp.dto.TelegramUserFtpDto;
import io.micronaut.serde.ObjectMapper;
import java.io.IOException;

public class TelegramUserFtpDtoMapper {

  public TelegramUserFtpDto telegramUserToTelegramUserFtpDto(TelegramUser telegramUser) {

    TelegramUserFtpDto telegramUserFtpDTO = new TelegramUserFtpDto();
    telegramUserFtpDTO.setId(telegramUser.getId());
    telegramUserFtpDTO.setIsBot(telegramUser.getIsBot());
    telegramUserFtpDTO.setLanguageCode(telegramUser.getLanguageCode());
    telegramUserFtpDTO.setCanJoinGroups(telegramUser.getCanJoinGroups());
    telegramUserFtpDTO.setCanReadAllGroupMessages(telegramUser.getCanReadAllGroupMessages());
    telegramUserFtpDTO.setSupportInlineQueries(telegramUser.getSupportInlineQueries());
    telegramUserFtpDTO.setIsPremium(telegramUser.getIsPremium());
    telegramUserFtpDTO.setAddedToAttachmentMenu(telegramUser.getAddedToAttachmentMenu());

    return telegramUserFtpDTO;
  }

  public TelegramUser telegramUserFtpDtoAsJsonToTelegramUser(String telegramUserFtpDtoAsJson) {
    TelegramUser telegramUser = new TelegramUser();

    ObjectMapper objectMapper = ObjectMapper.getDefault();
    try {
      TelegramUserFtpDto telegramUserFtpDto = objectMapper.readValue(telegramUserFtpDtoAsJson,
          TelegramUserFtpDto.class);

      telegramUser.setId(telegramUserFtpDto.getId());
      telegramUser.setIsBot(telegramUserFtpDto.getIsBot());
      telegramUser.setLanguageCode(telegramUserFtpDto.getLanguageCode());
      telegramUser.setCanJoinGroups(telegramUserFtpDto.getCanJoinGroups());
      telegramUser.setCanReadAllGroupMessages(telegramUserFtpDto.getCanReadAllGroupMessages());
      telegramUser.setSupportInlineQueries(telegramUserFtpDto.getSupportInlineQueries());
      telegramUser.setIsPremium(telegramUserFtpDto.getIsPremium());
      telegramUser.setAddedToAttachmentMenu(telegramUserFtpDto.getAddedToAttachmentMenu());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return telegramUser;
  }
}
