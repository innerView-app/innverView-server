package com.dev.innverview.video.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VideoContentRepository extends JpaRepository<VideoContent, UUID> {
    List<VideoContent> findByCreatedAtAfterOrderByCreatedAtAsc(LocalDateTime after, Pageable pageable);
    List<VideoContent> findAllByOrderByCreatedAtAsc(Pageable pageable);
}
