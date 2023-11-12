package com.marcosflobo.inputsong.controller;

import com.marcosflobo.inputsong.dto.RequestSong;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/v1/send")
public class SendSongController {

  @Post("/song")
  public HttpResponse<String> sendSong(@Body RequestSong song) {

    return HttpResponse.ok();
  }
}
