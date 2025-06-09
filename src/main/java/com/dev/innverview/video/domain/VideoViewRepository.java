package com.dev.innverview.video.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.innverview.video.domain.VideoContent;
import java.util.UUID;

public interface VideoViewRepository extends JpaRepository<VideoView, UUID> {
    long countByContent(VideoContent content);
}
