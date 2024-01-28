package com.marcosflobo.usecases;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

@Slf4j
@Singleton
public class FtpDatastore {

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
  private final FTPClient ftpClient;

  public FtpDatastore() {
    ftpClient = new FTPClient();
  }

  public Map<String, SongFtpDto> getSongsFile() {

    Map<String, SongFtpDto> songFtpDtoMap = new HashMap<>();
    try {
      connect();

      InputStream inputStream = ftpClient.retrieveFileStream(filePath);
      InputStreamReader reader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(reader);

      buildSongMap(bufferedReader, songFtpDtoMap);

      bufferedReader.close();
      reader.close();
      inputStream.close();

      disconnect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return songFtpDtoMap;
  }

  public void buildSongMap(BufferedReader bufferedReader,
      Map<String, SongFtpDto> songFtpDtoMap) throws IOException {
    String commaSeparatedLine;
    while ((commaSeparatedLine = bufferedReader.readLine()) != null) {
      String[] split = commaSeparatedLine.split(",");
      SongFtpDto songFtpDto = new SongFtpDto();
      songFtpDto.setTargetDate(split[0]);
      songFtpDto.setUrl(split[1]);

      songFtpDtoMap.put(songFtpDto.getTargetDate(), songFtpDto);
    }
  }

  public void save(Map<String, SongFtpDto> songFtpDtoList) {

    try {
      connect();

      StringBuilder content = buildSongsListFile(songFtpDtoList);
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
          content.toString().getBytes(
              StandardCharsets.UTF_8));
      ftpClient.storeFile(filePath, byteArrayInputStream);

      byteArrayInputStream.close();

      disconnect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public StringBuilder buildSongsListFile(Map<String, SongFtpDto> songFtpDtoList) {
    StringBuilder content = new StringBuilder();
    for (Map.Entry<String, SongFtpDto> entry : songFtpDtoList.entrySet()) {
      content.append(entry.getValue().getTargetDate()).append(",")
          .append(entry.getValue().getUrl()).append("\n");
    }
    return content;
  }

  private void disconnect() throws IOException {
    ftpClient.logout();
  }

  private void connect() throws IOException {
    ftpClient.connect(ftpServer, ftpPort);
    ftpClient.login(ftpUser, ftpPassword);
    ftpClient.enterLocalPassiveMode();
    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
  }
}
