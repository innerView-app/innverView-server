package com.dev.innverview.video.service.storage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LocalStorageService implements StorageService {

    private final ResourceLoader resourceLoader;

    @Value("${file.video-path}")
    String basePath;

    public LocalStorageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<String> listFiles(String folderName) throws IOException {
        Resource resource = resourceLoader.getResource(basePath + folderName);
        Path path = resource.getFile().toPath();
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Directory not found: " + basePath);
        }

        try (Stream<Path> filesStream = Files.walk(path, 1)) { // try-with-resources로 스트림 관리
            return filesStream
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(String objectName) {

    }

    @Override
    public void createFolder(String folderName) {

    }

    @Override
    public void upload(String objectName) {

    }

    @Override
    public InputStream readFile(String videoId, String target) throws IOException {
        Resource resource = resourceLoader.getResource(basePath + videoId + "/" + target);
        Path path = resource.getFile().toPath();
        if (!Files.exists(path)) {
            Logger logger = org.slf4j.LoggerFactory.getLogger(LocalStorageService.class);
            logger.error("File not found: {}", path);
        }
        return Files.newInputStream(path);
    }
}
