package com.dev.innverview.video.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.innverview.video.domain.VideoContent;
import java.util.List;
import java.util.UUID;

public interface VideoCommentRepository extends JpaRepository<VideoComment, UUID> {
    List<VideoComment> findByContent(VideoContent content);
}
