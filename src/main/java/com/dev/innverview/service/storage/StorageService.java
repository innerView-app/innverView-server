package com.dev.innverview.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface StorageService {

    List<String> listFiles(String folderName) throws IOException;

    void delete(String objectName);

    void createFolder(String folderName);

    void upload(String objectName);

    InputStream readFile(String videoId, String objectName) throws IOException;
}
