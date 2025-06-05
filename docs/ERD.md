# 데이터베이스 ERD

- **users**: 로컬 사용자 정보를 저장합니다.
  - id (PK)
  - email (unique)
  - username
  - profile_image
  - password
  - created_at
  - updated_at

- **oauth_users**: OAuth 로그인 정보를 저장합니다.
  - id (PK)
  - user_id (FK: users.id)
  - provider
  - provider_id
  - access_token
  - refresh_token
  - expires_at
  - created_at
  - updated_at

- **inner_views**: 기본 예제 엔티티
  - id (PK)
  - title
  - type
  - created_at

- **video_projects**: 비디오 편집 프로젝트
  - id (PK)
  - user_id (FK: users.id)
  - project_name
  - video_file_name
  - edit_data
  - created_at

- **video_contents**: 편집을 마친 비디오와 HLS 정보
  - id (PK)
  - project_id (FK: video_projects.id)
  - original_file_name
  - hls_path
  - created_at

- **video_processes**: HLS 변환 처리 상태
  - id (PK)
  - content_id (FK: video_contents.id)
  - status
  - message
  - updated_at

- **video_views**: 비디오 조회 기록
  - id (PK)
  - content_id (FK: video_contents.id)
  - user_id (FK: users.id)
  - created_at

- **video_likes**: 비디오 좋아요
  - id (PK)
  - content_id (FK: video_contents.id)
  - user_id (FK: users.id)
  - created_at

- **video_comments**: 비디오 댓글
  - id (PK)
  - content_id (FK: video_contents.id)
  - user_id (FK: users.id)
  - text
  - created_at
