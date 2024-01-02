package com.marcosflobo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.marcosflobo.storage.RedisServiceUsers;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BackUpUsersTest {

  @Container
  public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis/redis-stack-server:6.2.6-v10"))
      .withExposedPorts(6379);


  @Inject
  RedisServiceUsers redisServiceUsers;

  private Map<String, Object> getProperties() {
    if (!redis.isRunning()) {
      redis.start();
    }
    return Collections.singletonMap(
        "redis.uri", "redis://" + redis.getHost() + ":" + redis.getFirstMappedPort());
  }

  @BeforeEach
  void setUp() {
    String address = redis.getHost();
    Integer port = redis.getFirstMappedPort();

    System.out.println("address: " + address);
    System.out.println("puerto: " + port);
  }

  @Test
  void usersAreProperlyBackup() {

    try (EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, getProperties())) {

      assertNotNull(redisServiceUsers);
    }
    // add uses to the store

    // backup the users

    // check the users are in the backup
  }
}
