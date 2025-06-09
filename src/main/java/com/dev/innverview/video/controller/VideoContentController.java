package com.dev.innverview.video.controller;

import com.dev.innverview.video.domain.VideoContent;
import com.dev.innverview.video.service.VideoContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dev.innverview.util.VideoUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
    public ResponseEntity<StreamingResponseBody> stream(@PathVariable UUID projectId,
                                                        @PathVariable UUID contentId,
                                                        @PathVariable String file) throws IOException {
        VideoContent content = service.findById(contentId);
        Path path = service.getHlsPath(content).resolve(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (file.endsWith(".m3u8")) {
            headers.setContentDispositionFormData("attachment", "index.m3u8");
            BufferedReader reader = Files.newBufferedReader(path);
            String prefix = String.format("/api/projects/%s/final/hls/%s/", projectId, contentId);
            StreamingResponseBody body = VideoUtil.streamIndex(reader, prefix);
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        }

        headers.setContentDispositionFormData("attachment", file);
        InputStream input = Files.newInputStream(path);
        StreamingResponseBody body = VideoUtil.streamFile(input);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
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
