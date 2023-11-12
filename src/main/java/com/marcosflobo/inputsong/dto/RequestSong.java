package com.marcosflobo.inputsong.dto;


import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable.Deserializable
public class RequestSong {

  private String url;
}
