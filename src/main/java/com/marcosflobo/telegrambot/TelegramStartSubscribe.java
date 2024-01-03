package com.marcosflobo.telegrambot;

import com.marcosflobo.storage.UsersService;
import com.marcosflobo.storage.mysql.UserMysqlRepository;
import com.marcosflobo.storage.mysql.dto.TelegramUser;
import com.marcosflobo.storage.mysql.dto.TelegramUserData;
import com.marcosflobo.storage.mysql.dto.TelegramUserDataMapper;
import io.micronaut.data.exceptions.EmptyResultException;
import jakarta.inject.Singleton;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

@Singleton
@Setter
@Getter
@Slf4j
public class TelegramStartSubscribe implements TelegramCommand{

  private User user;

  private final UsersService usersService;
  private final UserMysqlRepository userMysqlRepository;
  private final TelegramUserDataMapper telegramUserDataMapper;

  public TelegramStartSubscribe(UsersService usersService, UserMysqlRepository userMysqlRepository,
      TelegramUserDataMapper telegramUserDataMapper) {
    this.usersService = usersService;
    this.userMysqlRepository = userMysqlRepository;
    this.telegramUserDataMapper = telegramUserDataMapper;
  }

  @Override
  public void execute() {

    // Add user to the database
    Long userId = user.getId();
    TelegramUserData telegramUserData = telegramUserDataMapper.fromUserToTelegramUserData(
        user);
    try {
      TelegramUser byTelegramUserId = userMysqlRepository.getByTelegramUserId(userId);
      log.info("User '{}' already exists, updating data in database", userId);
      byTelegramUserId.setTelegramUserData(telegramUserData);
      userMysqlRepository.update(byTelegramUserId);
    } catch (EmptyResultException exception) {
      log.info("New user '{}', Adding to the database", userId);
      userMysqlRepository.save(userId, telegramUserData);
    }
    List<TelegramUser> all = userMysqlRepository.findAll();
    log.info("List of users in MySQL: {}", all.toString());
    List<String> usersServiceAll = usersService.getAll();
    log.info("Users in Redis: {}", usersServiceAll.toString());
  }
}
