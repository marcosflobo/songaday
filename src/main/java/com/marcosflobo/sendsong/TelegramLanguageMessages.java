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

  private static final Map<String, Map<String, String>> messages = new HashMap<>() {{
    put("en", new HashMap<>() {{
      put("USER_SUBSCRIBED", "Yeah!💪 You are subscribed now!");
    }});
    put("es", new HashMap<>() {{
      put("USER_SUBSCRIBED", "¡Genial!💪 ¡Ya estás suscrito!");
    }});
  }};

  private final String[] langCodesAllowed = new String[]{"en", "es"};

  public String userSubscribed(final User user) {

    return getTelegramMessage(user.getLanguageCode(), "USER_SUBSCRIBED");
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
