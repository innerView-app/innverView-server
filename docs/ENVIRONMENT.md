# 환경 변수 설정

모든 환경 변수는 프로젝트 루트의 `.env` 파일에서 관리합니다. 예시는 `.env.example`을 참고하세요.

필수 변수
----------
- `KAKAO_CLIENT_ID`, `KAKAO_CLIENT_SECRET`, `KAKAO_REDIRECT_URI`
- `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET`, `GOOGLE_REDIRECT_URI`
- `SPRINGBOOT_PORT`
- 데이터베이스 접속 정보(`host`, `DB_PORT`, `database`, `username`, `password`)
- `JWT_SECRET` – JWT 서명을 위한 비밀 키

선택 변수
---------
- `JWT_EXPIRATION` – 토큰 만료 시간(밀리초), 기본값 3600000
- `UPLOAD_PATH` – 업로드 파일 저장 경로

`.env.example` 파일을 복사하여 `.env`로 사용하고, 위 값들을 환경에 맞게 수정하세요.
