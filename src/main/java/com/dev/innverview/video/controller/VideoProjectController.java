package com.dev.innverview.video.controller;

import com.dev.innverview.video.domain.VideoProject;
import com.dev.innverview.video.service.VideoProjectService;
import com.dev.innverview.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class VideoProjectController {

    private final VideoProjectService service;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<VideoProject> upload(@AuthenticationPrincipal UserDetails user,
                                               @RequestParam String projectName,
                                               @RequestParam String editData,
                                               @RequestPart MultipartFile video) throws IOException {
        VideoProject saved = service.saveProject(
                user != null ? userService.getByEmail(user.getUsername()) : null,
                projectName, editData, video);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<VideoProject> list(@AuthenticationPrincipal UserDetails user) {
        return service.list(user != null ? userService.getByEmail(user.getUsername()) : null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoProject> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
