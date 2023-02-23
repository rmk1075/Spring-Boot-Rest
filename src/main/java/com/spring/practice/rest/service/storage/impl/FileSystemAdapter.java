package com.spring.practice.rest.service.storage.impl;

import com.spring.practice.rest.service.storage.StorageServiceAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.stereotype.Service;

/**
 * File system storage adapter class.
 */
@Service
public class FileSystemAdapter implements StorageServiceAdapter {

  private static final String PATH = System.getProperty("user.dir") + "/resources/storage";
  private final String scheme = "file";

  FileSystemAdapter() throws IOException {
    if (!Files.exists(Path.of(PATH))) {
      Files.createDirectories(Path.of(PATH));
    }
  }

  @Override
  public String getScheme() {
    return this.scheme;
  }

  @Override
  public byte[] get(String url) throws URISyntaxException, IOException {
    URI uri = new URI(url);
    Path path = Path.of(uri.getPath());
    byte[] file = Files.readAllBytes(path);
    return file;
  }

  @Override
  public File getZip(String url) throws URISyntaxException, IOException {
    URI uri = new URI(url);
    Path srcPath = Path.of(uri.getPath());
    Path zipPath = Path.of(this.createTmpFile(UUID.randomUUID().toString() + ".zip"));
    Path zip = this.compress(srcPath, zipPath);
    return zip.toFile();
  }

  @Override
  public String create(String url, byte[] bytes) throws URISyntaxException, IOException {
    URI uri = new URI(url);
    Path path = Path.of(uri.getPath());
    if (!Files.exists(path.getParent())) {
      Files.createDirectories(path.getParent());
    }

    // create new file
    File file = path.toFile();
    file.createNewFile();

    // write contents
    if (bytes != null) {
      path = Files.write(path, bytes);
    }

    return path.toString();
  }

  @Override
  public String delete(String url) throws URISyntaxException, IOException {
    URI uri = new URI(url);
    Path path = Path.of(uri.getPath());
    Files.deleteIfExists(path);
    return path.toString();
  }

  private String createTmpFile(String filenanme) throws URISyntaxException, IOException {
    String url = String.join(
        "/",
        String.format("%s:/", this.scheme),
        FileSystemAdapter.PATH,
        "tmp",
        filenanme
    );
    return this.create(url, null);
  }

  private Path compress(Path srcPath, Path zipPath) throws IOException {
    FileOutputStream fos = new FileOutputStream(zipPath.toString());
    ZipOutputStream zos = new ZipOutputStream(fos);

    this.compressFile(srcPath.toString(), srcPath.getParent().toString(), zos);

    zos.closeEntry();
    zos.close();
    fos.close();
    return zipPath;
  }

  private void compressFile(
      String filePath,
      String basePath,
      ZipOutputStream zos
  ) throws IOException {
    File f = new File(filePath);
    if (f.isDirectory()) {
      for (File file : f.listFiles()) {
        this.compressFile(file.getPath(), basePath, zos);
      }
    } else {
      String relativePath = f.getPath().substring(basePath.length() + 1);
      ZipEntry zipEntry = new ZipEntry(relativePath);
      zos.putNextEntry(zipEntry);

      FileInputStream fis = new FileInputStream(f);
      byte[] bytes = new byte[1024];
      int len = 0;
      while (0 < (len = fis.read(bytes))) {
        zos.write(bytes, 0, len);
      }
      fis.close();
    }
  }
}
