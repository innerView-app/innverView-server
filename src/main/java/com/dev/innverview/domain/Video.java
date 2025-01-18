package com.dev.innverview.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(columnList = "createAt DESC")})
public class Video {
    @Version
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private Instant version;

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;


//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    User owner;
    String owner;

    @Column(length = 256, nullable = false)
    private String title;

    @Column(length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoStatus status = VideoStatus.PROCESSING;

    @Column(length = 1024, nullable = false)
    private String eventResult = "";

    @Column(length = 512, nullable = false)
    private String path = "";

    @Column(nullable = false)
    private Instant createAt;

    @PrePersist
    private void prePersist() {
        createAt = Instant.now();
    }

    public Video(String user, String title) {
        this.owner = user;
        this.title = title;
    }
}
