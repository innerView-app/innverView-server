package com.dev.innverview.controller;

import com.dev.innverview.domain.VideoProject;
import com.dev.innverview.service.VideoProjectService;
import com.dev.innverview.service.UserService;
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
        if (user == null) {
            throw new IllegalArgumentException("unauthenticated");
        }
        VideoProject saved = service.saveProject(userService.findByEmail(user.getUsername()),
                projectName, editData, video);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<VideoProject> list(@AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            throw new IllegalArgumentException("unauthenticated");
        }
        return service.list(userService.findByEmail(user.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoProject> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
