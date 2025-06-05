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
