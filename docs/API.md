# API 문서

## 인증
- `POST /auth/register` : 로컬 사용자 회원가입
- `/oauth2/authorize/{provider}` : OAuth2 로그인 시작 (kakao, google)

## InnerView
- `POST /api/innerviews`
- `GET /api/innerviews`
- `GET /api/innerviews/{id}`

## 비디오 프로젝트
- `POST /api/projects` (multipart/form-data)
- `GET /api/projects`
- `GET /api/projects/{id}`
- `POST /api/projects/{id}/final` 업로드된 프로젝트의 완성 비디오 업로드
- `GET /api/projects/{projectId}/final/original?contentId=` 완성 비디오 원본 다운로드
- `GET /api/projects/{projectId}/final/hls/{contentId}/{file}` HLS 스트리밍 파일 제공
- `GET /api/projects/{projectId}/final/status/{contentId}` HLS 처리 상태 조회

## 비디오 상호작용
- `POST /api/videos/{id}/views` 조회수 기록
- `POST /api/videos/{id}/likes` 좋아요 등록
- `POST /api/videos/{id}/comments` 댓글 작성
- `GET /api/videos/{id}/comments` 댓글 목록 조회

## 사용자 정보
- `GET /api/me` 내 정보 조회
- `PUT /api/me` 내 정보 수정
- `GET /api/my/videos` 내가 업로드한 비디오 목록 조회

## 쇼츠 피드
- `GET /api/shorts` : 연속 재생을 위한 다음 비디오 목록 조회
