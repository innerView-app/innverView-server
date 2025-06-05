package com.dev.innverview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoProcessRepository extends JpaRepository<VideoProcess, UUID> {
    VideoProcess findByContent(VideoContent content);
}
