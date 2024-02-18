package com.marcosflobo.usecases.unsubscribetelegramuser;

import org.telegram.telegrambots.meta.api.objects.User;

public interface UnSubscribeUser {

  void unsubscribe(User user);

}
