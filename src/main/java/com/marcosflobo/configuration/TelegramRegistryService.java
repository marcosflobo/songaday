package com.marcosflobo.configuration;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class TelegramRegistryService implements ApplicationEventListener<StartupEvent> {

  @Inject
  List<LongPollingBot> longPollingBots;
  @Property(name = "datasources.default.url")
  private String databaseHost;

  @Override
  public void onApplicationEvent(StartupEvent event) {
    TelegramBotsApi botsApi;
    try {
      botsApi = new TelegramBotsApi(DefaultBotSession.class);

      log.info("Database connection string {}", databaseHost);
      log.info("Registering Longpolling Bots");
      for (LongPollingBot bot : longPollingBots) {
        try {
          botsApi.registerBot(bot);
          log.info("Bot Registered {}", bot.getBotUsername());
        } catch (TelegramApiRequestException e) {
          log.error("Error registering bot {}: {}", bot, e.toString());
        }
      }
      log.info("Finished registering bots");
    } catch (TelegramApiException e) {
      log.error("Error loading Telegram bots: {}", e.toString());
    }

  }

}
