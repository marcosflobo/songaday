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

  private static final String USER_SUBSCRIBED = "USER_SUBSCRIBED";
  private static final String USER_UNSUBSCRIBED = "USER_UNSUBSCRIBED";
  private static final Map<String, Map<String, String>> messages = new HashMap<>() {{
    put("en", new HashMap<>() {{
      put(USER_SUBSCRIBED, "Yeah!💪 You are subscribed now!");
      put(USER_UNSUBSCRIBED, "Well!👋 It was nice while it lasted. You can resubscribe at any time using the menu option /subscribe");
    }});
    put("es", new HashMap<>() {{
      put(USER_SUBSCRIBED, "¡Genial!💪 ¡Ya estás suscrito!");
      put(USER_UNSUBSCRIBED, "¡Bueno!👋 ¡Fue bonito mientras duró! Puedes volver a subscribirte en cualquier momento usando la opción de menú /subscribe");
    }});
  }};

  private final String[] langCodesAllowed = new String[]{"en", "es"};

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
