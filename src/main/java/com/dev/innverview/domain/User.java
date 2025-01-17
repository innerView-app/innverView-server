package com.dev.innverview.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false, unique = true)
    private String email; // 사용자 이메일 (OAuth 로그인 시 식별자)

    @Column(nullable = true)
    private String username; // 서비스 내에서 사용할 사용자명 (필요 시)

    @Column(nullable = true)
    private String profileImage; // 프로필 이미지 URL

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 계정 생성 시간

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 계정 정보 마지막 업데이트 시간

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
