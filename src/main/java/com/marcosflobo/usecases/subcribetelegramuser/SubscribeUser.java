package com.marcosflobo.usecases.subcribetelegramuser;

import org.telegram.telegrambots.meta.api.objects.User;

public interface SubscribeUser {

  void subscribe(User user);
}
