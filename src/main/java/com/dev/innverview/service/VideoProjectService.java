package com.dev.innverview.service;

import com.dev.innverview.domain.User;
import com.dev.innverview.domain.VideoProject;
import com.dev.innverview.domain.VideoProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoProjectService {

    private final VideoProjectRepository repository;

    @Value("${uploads.path}")
    private String uploadDir;

    public VideoProject saveProject(User user, String projectName, String editData, MultipartFile video) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }

        String fileName = UUID.randomUUID() + "_" + video.getOriginalFilename();
        Path target = Paths.get(uploadDir).resolve(fileName);
        Files.createDirectories(target.getParent());
        Files.write(target, video.getBytes());

        VideoProject project = VideoProject.builder()
                .user(user)
                .projectName(projectName)
                .editData(editData)
                .videoFileName(fileName)
                .build();
        return repository.save(project);
    }

    public List<VideoProject> list(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }
        return repository.findByUser(user);
    }

    public VideoProject findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("project not found"));
    }
}
