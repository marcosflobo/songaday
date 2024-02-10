package com.marcosflobo.storage.ftp.user;

import com.marcosflobo.entity.TelegramUser;
import com.marcosflobo.storage.ftp.AbstractFtpDatastore;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import lombok.Setter;

@Setter
@Singleton
public class FtpDatastoreUser extends AbstractFtpDatastore {

  private TelegramUser user;

  @Property(name = "ftp.users.filePath")
  private String filePath;

  @Override
  public String getFilePath() {
    return filePath;
  }
}
