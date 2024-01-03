package com.marcosflobo.telegrambot;

import com.marcosflobo.storage.UsersService;
import com.marcosflobo.storage.mysql.UserMysqlRepository;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

@Singleton
@Getter
@Setter
@Slf4j
public class TelegramUnsubscribe implements TelegramCommand{

  private User user;
  private final UsersService usersService;
  private final UserMysqlRepository userMysqlRepository;

  public TelegramUnsubscribe(UsersService usersService, UserMysqlRepository userMysqlRepository) {
    this.usersService = usersService;
    this.userMysqlRepository = userMysqlRepository;
  }

  @Override
  public void execute() {

    Long userId = user.getId();
    usersService.delete(userId);
    userMysqlRepository.deleteByTelegramUserId(userId);
  }
}
