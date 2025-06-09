package com.dev.innverview.video.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.innverview.user.domain.User;
import java.util.UUID;

public interface VideoProjectRepository extends JpaRepository<VideoProject, UUID> {
    java.util.List<VideoProject> findByUser(User user);
}
