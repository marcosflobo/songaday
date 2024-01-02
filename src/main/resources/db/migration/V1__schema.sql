DROP TABLE IF EXISTS telegram_user;

CREATE TABLE telegram_user (
    id   BIGINT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    telegram_user_id   BIGINT NOT NULL UNIQUE,
   telegram_user_data  TEXT NOT NULL
);