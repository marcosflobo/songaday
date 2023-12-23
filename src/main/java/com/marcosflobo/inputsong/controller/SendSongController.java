package com.marcosflobo.inputsong.controller;

import com.marcosflobo.inputsong.dto.RequestSong;
import com.marcosflobo.inputsong.TelegramBotService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/v1/send")
public class SendSongController {

  private final TelegramBotService telegramBotService;

  public SendSongController(TelegramBotService telegramBotService) {
    this.telegramBotService = telegramBotService;
  }

  @Post("/song")
  public HttpResponse<String> sendSong(@Body RequestSong song) {

    log.info(String.valueOf(song));
    telegramBotService.sendMessage(song.getUrl());
    return HttpResponse.ok();
  }
}
