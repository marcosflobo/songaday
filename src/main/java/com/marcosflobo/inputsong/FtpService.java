package com.marcosflobo.inputsong;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

@Slf4j
@Singleton
public class FtpService {

  @Property(name = "ftp.server")
  private String ftpServer;

  @Property(name = "ftp.port")
  private int ftpPort;

  @Property(name = "ftp.user")
  private String ftpUser;

  @Property(name = "ftp.password")
  private String ftpPassword;

  @Property(name = "ftp.filePath")
  private String filePath;

  public List<String> getCsv() {

    FTPClient ftpClient = new FTPClient();

    List<String> songList = new ArrayList<>();
    try {
      ftpClient.connect(ftpServer, ftpPort);
      ftpClient.login(ftpUser, ftpPassword);
      ftpClient.enterLocalPassiveMode();
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

      InputStream inputStream = ftpClient.retrieveFileStream(filePath);
      InputStreamReader reader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(reader);

      String commaSeparatedLine;
      log.info("Loading list of songs from server...");
      while ((commaSeparatedLine = bufferedReader.readLine()) != null) {
        log.debug(commaSeparatedLine);
        songList.add(commaSeparatedLine);
      }
      log.info("List of {} songs loaded!", songList.size());

      bufferedReader.close();
      reader.close();
      inputStream.close();
      ftpClient.logout();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return songList;
  }
}
