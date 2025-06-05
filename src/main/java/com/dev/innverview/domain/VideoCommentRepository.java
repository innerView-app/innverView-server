package com.dev.innverview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VideoCommentRepository extends JpaRepository<VideoComment, UUID> {
    List<VideoComment> findByContent(VideoContent content);
}
