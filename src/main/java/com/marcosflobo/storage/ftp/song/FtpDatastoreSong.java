package com.marcosflobo.storage.ftp.song;

import com.marcosflobo.storage.ftp.AbstractFtpDatastore;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named("ftpDatastoreSong")
@Singleton
public class FtpDatastoreSong extends AbstractFtpDatastore {

  @Property(name = "ftp.filePath")
  private String filePath;

  @Override
  public String getFilePath() {
    return filePath;
  }
}
