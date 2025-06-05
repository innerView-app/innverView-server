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
