package com.marcosflobo.usecases;

import com.marcosflobo.storage.RedisServiceUsers;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class UseCaseBackupUsers {

  private final RedisServiceUsers redisServiceUsers;
  @Property(name = "ftp.backupfiles.users")
  private String backupFileUsers;

  public UseCaseBackupUsers(RedisServiceUsers redisServiceUsers) {
    this.redisServiceUsers = redisServiceUsers;
  }

  public void backup() {

    // Get users
    List<String> users = redisServiceUsers.getAll();

    // run the backup in a file

  }
}
