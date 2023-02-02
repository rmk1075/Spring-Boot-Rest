package com.spring.practice.rest.service.storage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.practice.rest.service.storage.impl.FileSystemAdapter;

@Service
public class StorageService {

    @Autowired
    FileSystemAdapter fileSystemService;

    private final StorageServiceAdapter[] ADAPTERS = new StorageServiceAdapter[]{fileSystemService};

    private StorageServiceAdapter adapter(String url) throws URISyntaxException, IllegalArgumentException {
        URI uri = new URI(url);
        String scheme = uri.getScheme();
        for (StorageServiceAdapter storageServiceAdapter : ADAPTERS) {
            if(storageServiceAdapter.getScheme().equals(scheme)) {
                return storageServiceAdapter;
            }
        }

        String exception = String.format("Scheme[%s] is not supported. Supported schemes are [%s]",
            scheme, Arrays.stream(ADAPTERS).map(adpt -> adpt.getScheme()));
        throw new IllegalArgumentException(exception);
    }

    public byte[] get(String url) throws IllegalArgumentException, URISyntaxException, IOException {
        return this.adapter(url).get(url);
    }

    public String create(String url, byte[] bytes) throws IllegalArgumentException, URISyntaxException, IOException {
        return this.adapter(url).create(url, bytes);
    }

    public void delete(String url) throws IllegalArgumentException, URISyntaxException, IOException {
        this.adapter(url).delete(url);
    }
    
}
