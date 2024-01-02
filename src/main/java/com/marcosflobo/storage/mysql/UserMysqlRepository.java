package com.marcosflobo.storage.mysql;

import com.marcosflobo.storage.mysql.dto.TelegramUser;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import jakarta.annotation.Nonnull;


@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserMysqlRepository extends PageableRepository<TelegramUser, Long>{

  TelegramUser save(@Nonnull Long telegramUserId, @Nonnull String telegramUserData);
}
