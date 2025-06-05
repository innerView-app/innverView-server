package com.dev.innverview.controller;

import com.dev.innverview.domain.User;
import com.dev.innverview.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.getByEmail(user.getUsername()));
    }

    @PutMapping("/me")
    public ResponseEntity<User> update(@AuthenticationPrincipal UserDetails user,
                                       @RequestBody UpdateReq req) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.update(user.getUsername(), req.getUsername(), req.getProfileImage()));
    }

    @GetMapping("/my/videos")
    public ResponseEntity<?> myVideos(@AuthenticationPrincipal UserDetails user) {
        if (user == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(userService.myVideos(user.getUsername()));
    }

    @Data
    public static class UpdateReq {
        private String username;
        private String profileImage;
    }
}
