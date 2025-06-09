package com.dev.innverview.video.controller;

import com.dev.innverview.video.domain.VideoComment;
import com.dev.innverview.video.service.VideoInteractionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoInteractionController {

    private final VideoInteractionService service;

    @PostMapping("/{id}/views")
    public ResponseEntity<?> view(@PathVariable UUID id,
                                  @AuthenticationPrincipal UserDetails user) {
        service.addView(id, user != null ? user.getUsername() : null);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<?> like(@PathVariable UUID id,
                                  @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        service.like(id, user.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<VideoComment> comment(@PathVariable UUID id,
                                                @AuthenticationPrincipal UserDetails user,
                                                @RequestBody CommentReq req) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        VideoComment comment = service.comment(id, user.getUsername(), req.getText());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{id}/comments")
    public List<VideoComment> comments(@PathVariable UUID id) {
        return service.comments(id);
    }

    @Data
    public static class CommentReq { private String text; }
}
