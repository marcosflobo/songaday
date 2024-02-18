package com.marcosflobo.storage.ftp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

public interface FtpDatastore {

  void save(ByteArrayInputStream byteArrayInputStream);
  BufferedReader getFile();
}
