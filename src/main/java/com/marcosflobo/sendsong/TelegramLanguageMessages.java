package com.marcosflobo.sendsong;

import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Singleton
public class TelegramLanguageMessages {

  private static final String DAILY_SONG = "DAILY_SONG";
  private static final String NOT_SUPPORTED = "NOT_SUPPORTED";
  private static final String USER_SUBSCRIBED = "USER_SUBSCRIBED";
  private static final String USER_UNSUBSCRIBED = "USER_UNSUBSCRIBED";
  private static final String START = "START";
  private static final Map<String, Map<String, String>> messages = new HashMap<>() {{
    put("en", new HashMap<>() {{
      put(DAILY_SONG, "\uD83C\uDFB5\uD83D\uDC9CDaily song arrived!\uD83D\uDC47\uD83C\uDFFD\r\n\r\n");
      put(NOT_SUPPORTED, "Hi!👋 By the moment I'm not able to interact with you, I can just send you a daily song. /subscribe if you did not yet and enjoy!");
      put(USER_SUBSCRIBED, "Yeah!💪 You are subscribed now!");
      put(USER_UNSUBSCRIBED, "Well!👋 It was nice while it lasted. You can resubscribe at any time using the menu option /subscribe");
      put(START, "Hi there!👋 This bot can send you a song every day at 9 a.m. (CET) to delight your day! "
          + "You just have to click on the menu and select /subscribe");
    }});
    put("es", new HashMap<>() {{
      put(DAILY_SONG, "\uD83C\uDFB5\uD83D\uDC9C¡La canción del día!\uD83D\uDC47\uD83C\uDFFD\r\n\r\n");
      put(NOT_SUPPORTED, "¡Hola!👋 Por el momento no puedo interactuar contigo, solo puedo enviarte una canción diaria. ¡ /subscribe si aún no lo has hecho y disfruta!");
      put(USER_SUBSCRIBED, "¡Genial!💪 ¡Ya estás suscrito!");
      put(USER_UNSUBSCRIBED, "¡Bueno!👋 ¡Fue bonito mientras duró! Puedes volver a subscribirte en cualquier momento usando la opción de menú /subscribe");
      put(START, "¡Hola!👋 Este bot puede enviarte una canción diariamente a las 9 a.m. (CET) para alegrate el día. "
          + "Solo tienes que seleccionar la opción /subscribe en el menú de nuestra conversación");
    }});
  }};

  private final String[] langCodesAllowed = new String[]{"en", "es"};

  public String start(final String langCode) {
    return getTelegramMessage(langCode, START);
  }
  public String dailySong() {
    return getTelegramMessage("en", DAILY_SONG);
  }
  public String dailySong(final String langCode) {
    return getTelegramMessage(langCode, DAILY_SONG);
  }

  public String actionNotSupported(final User user) {

    return getTelegramMessage(user.getLanguageCode(), NOT_SUPPORTED);
  }
  public String userSubscribed(final User user) {

    return getTelegramMessage(user.getLanguageCode(), USER_SUBSCRIBED);
  }
  public String userUnSubscribed(final User user) {

    return getTelegramMessage(user.getLanguageCode(), USER_UNSUBSCRIBED);
  }

  private String getTelegramMessage(final String langCode, final String constantMessage) {

    log.info("Language code received: '{}'", langCode);
    String key = langCode;
    if (Arrays.stream(langCodesAllowed).noneMatch(langCode::equals)) {
      key = "en";
    }
    return messages.get(key).get(constantMessage);
  }

}
