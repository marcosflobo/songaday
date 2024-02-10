package com.marcosflobo.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.marcosflobo.entity.TelegramUser;
import com.marcosflobo.storage.UserDatastore;
import com.marcosflobo.storage.ftp.user.FtpDatastoreUser;
import com.marcosflobo.storage.ftp.FtpDatastore;
import com.marcosflobo.storage.ftp.user.UserDatastoreFtpFileImpl;
import com.marcosflobo.usecases.subcribetelegramuser.SubscribeUser;
import com.marcosflobo.usecases.subcribetelegramuser.UseCaseSubscribeTelegramUser;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

class UseCaseSubscribeTelegramUserTest {

  @Test
  void testSaveSubscribedUser() {

    try (GenericContainer<?> ftpContainer = new GenericContainer<>(
        DockerImageName.parse("garethflowers/ftp-server"))
        .withExposedPorts(21)
        .withEnv("FTP_USER", "testuser")
        .withEnv("FTP_USER", "testpass")
        .withEnv("FTP_PASSIVE_PORTS", "30000:30099")
        .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)))) {

      ftpContainer.start();

      String ftpHost = ftpContainer.getHost();
      int ftpPort = ftpContainer.getMappedPort(21);



    // GIVEN: A new user
    User telegramUser = new User();
    telegramUser.setId(9999L);

    // WHEN: It's subscribed
    FtpDatastore ftpDatastore = new FtpDatastoreUser();
    ((FtpDatastoreUser) ftpDatastore).setFtpServer(ftpHost);
    ((FtpDatastoreUser) ftpDatastore).setFtpPort(ftpPort);
    ((FtpDatastoreUser) ftpDatastore).setFtpUser("testuser");
    ((FtpDatastoreUser) ftpDatastore).setFtpPassword("testpass");
    ((FtpDatastoreUser) ftpDatastore).setFilePath("users-test.csv");
    UserDatastore userDatastore = new UserDatastoreFtpFileImpl(ftpDatastore);
    SubscribeUser useCaseSubscribeUser = new UseCaseSubscribeTelegramUser(userDatastore);
    useCaseSubscribeUser.subscribe(telegramUser);

    // THEN: It's stored
    TelegramUser found = userDatastore.findById(telegramUser.getId());

    assertEquals(telegramUser.getId(), found.getId());
    }

  }

}
