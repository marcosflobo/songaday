package com.marcosflobo.storage;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.io.ResourceLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Singleton;

@ConfigurationProperties("songs")
@Singleton
public class SongReader {

  private final ResourceLoader resourceLoader;
  private String filePath;

  public SongReader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public List<String> readTracksFromFile() throws IOException {
    InputStream inputStream = resourceLoader.getResourceAsStream(filePath).get();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      return reader.lines()
          .collect(Collectors.toList());
    }
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}
