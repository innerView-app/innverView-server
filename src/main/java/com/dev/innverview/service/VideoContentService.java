package com.dev.innverview.service;

import com.dev.innverview.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoContentService {

    private final VideoContentRepository contentRepository;
    private final VideoProjectRepository projectRepository;
    private final VideoProcessRepository processRepository;

    @Value("${uploads.path}")
    private String uploadDir;

    public VideoContent uploadFinalVideo(UUID projectId, MultipartFile video) throws IOException {
        VideoProject project = projectRepository.findById(projectId).orElseThrow();
        String fileName = UUID.randomUUID() + "_" + video.getOriginalFilename();
        Path originalDir = Paths.get(uploadDir, "final", "original");
        Files.createDirectories(originalDir);
        Path originalPath = originalDir.resolve(fileName);
        Files.write(originalPath, video.getBytes());

        // HLS directory
        Path hlsDir = Paths.get(uploadDir, "final", "hls", fileName);
        Files.createDirectories(hlsDir);
        Path playlist = hlsDir.resolve("index.m3u8");
        Files.writeString(playlist, "#EXTM3U\n");

        VideoContent content = VideoContent.builder()
                .project(project)
                .originalFileName(fileName)
                .hlsPath(hlsDir.toString())
                .build();
        contentRepository.save(content);

        VideoProcess process = VideoProcess.builder()
                .content(content)
                .status(VideoProcess.Status.PROCESSING)
                .build();
        processRepository.save(process);
        processVideoAsync(process, originalPath, playlist);
        return content;
    }

    public Path getOriginalPath(VideoContent content) {
        return Paths.get(uploadDir, "final", "original", content.getOriginalFileName());
    }

    public Path getHlsPath(VideoContent content) {
        return Paths.get(content.getHlsPath());
    }

    public VideoContent findById(UUID id) {
        return contentRepository.findById(id).orElseThrow();
    }

    public List<VideoContent> nextContents(UUID afterId, int size) {
        Pageable page = Pageable.ofSize(size);
        if (afterId == null) {
            return contentRepository.findAllByOrderByCreatedAtAsc(page);
        }
        VideoContent after = contentRepository.findById(afterId).orElse(null);
        if (after == null) {
            return contentRepository.findAllByOrderByCreatedAtAsc(page);
        }
        LocalDateTime ts = after.getCreatedAt();
        return contentRepository.findByCreatedAtAfterOrderByCreatedAtAsc(ts, page);
    }

    @Async
    public void processVideoAsync(VideoProcess process, Path source, Path playlist) {
        try {
            new ProcessBuilder(
                    "ffmpeg", "-i", source.toString(),
                    "-codec", "copy",
                    "-start_number", "0",
                    "-hls_time", "10",
                    "-hls_list_size", "0",
                    "-f", "hls",
                    playlist.toString()
            ).inheritIO().start().waitFor();
            process.setStatus(VideoProcess.Status.COMPLETED);
        } catch (Exception e) {
            process.setStatus(VideoProcess.Status.FAILED);
            process.setMessage(e.getMessage());
        } finally {
            processRepository.save(process);
        }
    }

    public VideoProcess status(VideoContent content) {
        return processRepository.findByContent(content);
    }
}
