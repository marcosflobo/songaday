package com.marcosflobo.sendsong.exception;

public class NoSongForTodayException extends Exception{

  public NoSongForTodayException() {
  }

  public NoSongForTodayException(String message) {
    super(message);
  }

  public NoSongForTodayException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSongForTodayException(Throwable cause) {
    super(cause);
  }

  public NoSongForTodayException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
