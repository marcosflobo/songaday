package com.marcosflobo.storage.ftp;

import io.micronaut.context.annotation.Property;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


@Getter
@Setter
@Slf4j
public abstract class AbstractFtpDatastore implements FtpDatastore {

  @Property(name = "ftp.server")
  private String ftpServer;

  @Property(name = "ftp.port")
  private int ftpPort;

  @Property(name = "ftp.user")
  private String ftpUser;

  @Property(name = "ftp.password")
  private String ftpPassword;

  private final FTPClient ftpClient;

  protected AbstractFtpDatastore() {
    this.ftpClient = new FTPClient();
  }

  @Override
  public void save(ByteArrayInputStream byteArrayInputStream) {

    try {
      connect();

      String filePath = getFilePath();
      ftpClient.storeFile(filePath, byteArrayInputStream);

      byteArrayInputStream.close();
      disconnect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public BufferedReader getFile() {
    try {
      connect();

      String filePath = getFilePath();
      InputStream in = ftpClient.retrieveFileStream(filePath);
      BufferedReader bufferedReader = null;
      if (in != null) {
        bufferedReader = new BufferedReader(new InputStreamReader(in));
      } else {
        log.info("The file '{}' was not found in the FTP server", filePath);
      }

      disconnect();

      return bufferedReader;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public abstract String getFilePath();

  private void disconnect() throws IOException {
    ftpClient.disconnect();
  }

  private void connect() throws IOException {
    ftpClient.connect(ftpServer, ftpPort);
    ftpClient.login(ftpUser, ftpPassword);
    ftpClient.enterLocalPassiveMode();
    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
  }
}
