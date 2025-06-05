package com.dev.innverview.controller;

import com.dev.innverview.domain.VideoContent;
import com.dev.innverview.service.VideoContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VideoContentController {

    private final VideoContentService service;

    @PostMapping("/projects/{id}/final")
    public ResponseEntity<VideoContent> uploadFinal(@PathVariable UUID id,
                                                    @RequestPart MultipartFile video) throws IOException {
        VideoContent content = service.uploadFinalVideo(id, video);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/projects/{projectId}/final/original")
    public ResponseEntity<FileSystemResource> downloadOriginal(@PathVariable UUID projectId,
                                                               @RequestParam UUID contentId) {
        VideoContent content = service.findById(contentId);
        Path path = service.getOriginalPath(content);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(path));
    }

    @GetMapping("/projects/{projectId}/final/hls/{contentId}/{file}")
    public ResponseEntity<FileSystemResource> stream(@PathVariable UUID projectId,
                                                     @PathVariable UUID contentId,
                                                     @PathVariable String file) {
        VideoContent content = service.findById(contentId);
        Path path = service.getHlsPath(content).resolve(file);
        return ResponseEntity.ok()
                .contentType(file.endsWith(".m3u8") ? MediaType.APPLICATION_OCTET_STREAM : MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(path));
    }

    @GetMapping("/projects/{projectId}/final/status/{contentId}")
    public ResponseEntity<?> status(@PathVariable UUID projectId, @PathVariable UUID contentId) {
        VideoContent content = service.findById(contentId);
        return ResponseEntity.ok(service.status(content));
    }

    @GetMapping("/shorts")
    public List<VideoContent> shorts(@RequestParam(required = false) UUID after,
                                     @RequestParam(defaultValue = "5") int size) {
        return service.nextContents(after, size);
    }
}
