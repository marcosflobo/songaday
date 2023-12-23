package com.marcosflobo.storage;

import java.util.List;

public interface UsersService {

  void add(Long userId);
  void delete(Long userId);
  List<String> getAll();
}
