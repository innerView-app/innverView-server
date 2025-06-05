package com.dev.innverview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoLikeRepository extends JpaRepository<VideoLike, UUID> {
    long countByContent(VideoContent content);
    boolean existsByContentAndUser(VideoContent content, User user);
}
