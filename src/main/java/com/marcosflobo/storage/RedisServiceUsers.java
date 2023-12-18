package com.marcosflobo.storage;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.List;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class RedisServiceUsers implements UsersService {

  private final StatefulRedisConnection<String, String> connection;

  public RedisServiceUsers(StatefulRedisConnection<String, String> connection) {
    this.connection = connection;
  }

  @Override
  public void add(Long userId) {

      RedisCommands<String, String> syncCommands = connection.sync();

      syncCommands.zadd("users", 0, userId.toString());
    List<String> users = syncCommands.zrange("users", 0, -1);
    log.info(users.toString());
  }

  @Override
  public void delete(Long userId) {

    RedisCommands<String, String> syncCommands = connection.sync();

    syncCommands.zrem("users", userId.toString());
  }
}
