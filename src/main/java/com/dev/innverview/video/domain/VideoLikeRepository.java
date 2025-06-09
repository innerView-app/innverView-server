package com.dev.innverview.video.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.innverview.video.domain.VideoContent;
import com.dev.innverview.user.domain.User;
import java.util.UUID;

public interface VideoLikeRepository extends JpaRepository<VideoLike, UUID> {
    long countByContent(VideoContent content);
    boolean existsByContentAndUser(VideoContent content, User user);
}
