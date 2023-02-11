package com.spring.practice.rest.service.storage;

import com.spring.practice.rest.service.storage.impl.FileSystemAdapter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

  @Autowired FileSystemAdapter fileSystemService;

  private StorageServiceAdapter adapter(String url)
      throws URISyntaxException, IllegalArgumentException {
    URI uri = new URI(url);
    String scheme = uri.getScheme();
    if (fileSystemService.getScheme().equals(scheme)) return fileSystemService;
    else {
      String exception =
          String.format(
              "Scheme[%s] is not supported. Supported schemes are [%s]",
              scheme, List.of(fileSystemService).stream().map(adpt -> adpt.getScheme()));
      throw new IllegalArgumentException(exception);
    }
  }

  public byte[] get(String url) throws IllegalArgumentException, URISyntaxException, IOException {
    return this.adapter(url).get(url);
  }

  public String create(String url, byte[] bytes)
      throws IllegalArgumentException, URISyntaxException, IOException {
    return this.adapter(url).create(url, bytes);
  }

  public String delete(String url)
      throws IllegalArgumentException, URISyntaxException, IOException {
    return this.adapter(url).delete(url);
  }
}
