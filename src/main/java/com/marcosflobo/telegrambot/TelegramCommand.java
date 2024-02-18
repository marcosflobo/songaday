package com.marcosflobo.telegrambot;

public interface TelegramCommand {

  void execute();
  String getMessage();
}
