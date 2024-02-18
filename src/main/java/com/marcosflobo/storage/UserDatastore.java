package com.marcosflobo.storage;

import com.marcosflobo.entity.TelegramUser;
import java.util.List;

public interface UserDatastore {

  void save(TelegramUser telegramUser);

  TelegramUser findById(Long id);
  void delete(TelegramUser telegramUser);

  List<TelegramUser> findAll();
}
