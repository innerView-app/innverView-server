CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255),
    profile_image VARCHAR(255),
    password VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE oauth_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    provider VARCHAR(50) NOT NULL,
    provider_id VARCHAR(100) NOT NULL,
    access_token TEXT,
    refresh_token TEXT,
    expires_at DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    UNIQUE KEY unique_provider_user (provider, provider_id),
    CONSTRAINT fk_oauth_user_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE inner_views (
    id CHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE video_projects (
    id CHAR(36) PRIMARY KEY,
    user_id BIGINT,
    project_name VARCHAR(255) NOT NULL,
    video_file_name VARCHAR(255) NOT NULL,
    edit_data TEXT,
    created_at DATETIME NOT NULL,
    CONSTRAINT fk_video_project_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE video_contents (
    id CHAR(36) PRIMARY KEY,
    project_id CHAR(36) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    hls_path VARCHAR(255) NOT NULL,
    created_at DATETIME,
    CONSTRAINT fk_video_content_project FOREIGN KEY (project_id) REFERENCES video_projects(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE video_processes (
    id CHAR(36) PRIMARY KEY,
    content_id CHAR(36) NOT NULL,
    status VARCHAR(20),
    message TEXT,
    updated_at DATETIME,
    CONSTRAINT fk_video_process_content FOREIGN KEY (content_id) REFERENCES video_contents(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE video_views (
    id CHAR(36) PRIMARY KEY,
    content_id CHAR(36) NOT NULL,
    user_id BIGINT,
    created_at DATETIME,
    CONSTRAINT fk_video_view_content FOREIGN KEY (content_id) REFERENCES video_contents(id),
    CONSTRAINT fk_video_view_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE video_likes (
    id CHAR(36) PRIMARY KEY,
    content_id CHAR(36) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at DATETIME,
    CONSTRAINT fk_video_like_content FOREIGN KEY (content_id) REFERENCES video_contents(id),
    CONSTRAINT fk_video_like_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE video_comments (
    id CHAR(36) PRIMARY KEY,
    content_id CHAR(36) NOT NULL,
    user_id BIGINT NOT NULL,
    text TEXT,
    created_at DATETIME,
    CONSTRAINT fk_video_comment_content FOREIGN KEY (content_id) REFERENCES video_contents(id),
    CONSTRAINT fk_video_comment_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
