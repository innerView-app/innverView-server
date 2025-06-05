# InnverView 서버

이 프로젝트는 [innerView 안드로이드 앱](https://github.com/innerView-app/innerView-android)에서 사용하는 Spring Boot 백엔드입니다. 카카오를 포함한 OAuth2 로그인과 **InnerView** 데이터를 관리하기 위한 REST API를 제공합니다.

## 요구 사항
- Java 17
- Gradle
- 프로덕션에서는 MySQL 사용, 테스트 시에는 H2 사용

## 실행 방법
1. `.env.example` 파일을 복사하여 `.env`로 저장한 후 값을 수정합니다.
2. 아래 명령어로 서버를 실행합니다.
   ```bash
   ./gradlew bootRun
   ```

## 데이터베이스 설정
MySQL 8.0 컨테이너를 사용한다면 다음과 같이 실행할 수 있습니다.
```bash
docker run --name innerview-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=innerview -d mysql:8.0
```
이후 `docs/mysql-schema.sql` 스크립트를 적용하여 테이블을 생성합니다.
```bash
docker exec -i innerview-mysql mysql -uroot -ppass innerview < docs/mysql-schema.sql
```

## 주요 환경 변수
모든 변수는 `.env.example` 파일을 참고하세요. 주요 변수는 다음과 같습니다.
- `KAKAO_CLIENT_ID`, `KAKAO_CLIENT_SECRET`, `KAKAO_REDIRECT_URI`
- `SPRINGBOOT_PORT` – 서버 포트
- 데이터베이스 접속 정보 (`host`, `DB_PORT` 등)

## API 개요
- `POST /api/innerviews` – InnerView 생성
- `GET /api/innerviews` – InnerView 목록 조회
- `GET /api/innerviews/{id}` – 특정 InnerView 조회
- `POST /auth/register` – 로컬 사용자 등록
- `POST /api/projects` – 비디오 프로젝트 업로드
- OAuth2 로그인 시작 경로: `/oauth2/authorization/{provider}` (kakao, google 지원). 로그인 성공 시 응답 헤더 `Authorization: Bearer <token>` 으로 JWT 토큰이 발급됩니다.

더 많은 API 정보는 docs/API.md 파일을 참고하세요.

## 테스트
단위 테스트 실행:
```bash
./gradlew test
```

## 라이선스
MIT
