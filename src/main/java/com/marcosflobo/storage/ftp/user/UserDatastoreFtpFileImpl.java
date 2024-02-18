package com.marcosflobo.storage.ftp.user;

import com.marcosflobo.entity.TelegramUser;
import com.marcosflobo.storage.UserDatastore;
import com.marcosflobo.storage.ftp.FtpDatastore;
import com.marcosflobo.storage.ftp.dto.TelegramUserFtpDto;
import com.marcosflobo.storage.ftp.mapper.TelegramUserFtpDtoMapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class UserDatastoreFtpFileImpl implements UserDatastore {

  private final FtpDatastore ftpDatastore;
  private final TelegramUserFtpDtoMapper telegramUserFtpDtoMapper;

  public UserDatastoreFtpFileImpl(@Named("ftpDatastoreUser") FtpDatastore ftpDatastore) {
    this.ftpDatastore = ftpDatastore;
    telegramUserFtpDtoMapper = new TelegramUserFtpDtoMapper();
  }

  @Override
  public void save(TelegramUser telegramUser) {
    BufferedReader bufferedReader = ftpDatastore.getFile();
    StringBuilder content = new StringBuilder();

    if (bufferedReader != null) {
      try {
        String commaSeparatedLine;
        while ((commaSeparatedLine = bufferedReader.readLine()) != null) {
          content.append(commaSeparatedLine).append("\n");
        }
        bufferedReader.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    if (telegramUser != null) {
      TelegramUserFtpDto telegramUserFtpDto = telegramUserFtpDtoMapper.telegramUserToTelegramUserFtpDto(telegramUser);
      content.append(telegramUserFtpDto);
    }
    ftpDatastore.save(new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8)));
  }

  @Override
  public TelegramUser findById(Long id) {
    BufferedReader file = ftpDatastore.getFile();
    if (file != null) {
      try {
        TelegramUser telegramUser = null;
        for (String line = file.readLine(); line != null; line = file.readLine()) {
          if (line.contains(id.toString())) {
            log.info("User '{}' found in database!", id);
            telegramUser = new TelegramUser();
            telegramUser.setId(id);
          }
        }
        file.close();
        return telegramUser;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return null;
  }

  @Override
  public void delete(TelegramUser telegramUser) {

    BufferedReader bufferedReader = ftpDatastore.getFile();
    StringBuilder content = new StringBuilder();

    if (bufferedReader != null) {
      try {
        String commaSeparatedLine;
        while ((commaSeparatedLine = bufferedReader.readLine()) != null) {
          if (!commaSeparatedLine.contains(telegramUser.getId().toString())) {
            content.append(commaSeparatedLine).append("\n");
          }
        }
        bufferedReader.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    ftpDatastore.save(new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8)));
  }

  @Override
  public List<TelegramUser> findAll() {

    List<TelegramUser> users = new ArrayList<>();
    BufferedReader file = ftpDatastore.getFile();
    if (file != null) {
      try {
        for (String telegramUserFtpDtoAsJson = file.readLine();
            telegramUserFtpDtoAsJson != null;
            telegramUserFtpDtoAsJson = file.readLine()) {
          users.add(telegramUserFtpDtoMapper.telegramUserFtpDtoAsJsonToTelegramUser(telegramUserFtpDtoAsJson));
        }
        file.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return users;
  }
}
