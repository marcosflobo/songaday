package com.marcosflobo.storage;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;

@Singleton
public class UsersServiceMemory implements UsersService {

  private final Set<Long> users;

  public UsersServiceMemory () {
    users = new HashSet<>();
  }

  public void add(Long userId) {
    users.add(userId);
  }

  public void delete(Long userId) {
    users.remove(userId);
  }

  public String toString() {
    return users.toString();
  }
}
