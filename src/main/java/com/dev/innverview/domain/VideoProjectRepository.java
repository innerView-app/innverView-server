package com.dev.innverview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoProjectRepository extends JpaRepository<VideoProject, UUID> {
    java.util.List<VideoProject> findByUser(User user);
}
