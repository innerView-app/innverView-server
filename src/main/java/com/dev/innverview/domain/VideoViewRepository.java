package com.dev.innverview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoViewRepository extends JpaRepository<VideoView, UUID> {
    long countByContent(VideoContent content);
}
