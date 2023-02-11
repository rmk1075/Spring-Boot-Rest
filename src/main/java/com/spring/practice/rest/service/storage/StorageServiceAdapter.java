package com.spring.practice.rest.service.storage;

import java.io.IOException;
import java.net.URISyntaxException;

public interface StorageServiceAdapter {

  public String getScheme();

  public byte[] get(String url) throws URISyntaxException, IOException;

  public String create(String url, byte[] bytes) throws URISyntaxException, IOException;

  public String delete(String url) throws URISyntaxException, IOException;
}
