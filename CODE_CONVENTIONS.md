# 코드 컨벤션

* Java 17 기능을 적극 활용합니다.
* 표준 Spring Boot 패키지 구조를 따릅니다.
* 모든 클래스는 `com.dev.innverview` 패키지 하위에 위치합니다.
* 반복되는 코드는 Lombok 어노테이션을 이용해 줄입니다.
* 테스트는 JUnit 5를 사용하며 `src/test/java` 하위에 동일한 패키지 구조로 작성합니다.
* REST 엔드포인트 추가 시 `@RestController`를 사용하고 `ResponseEntity`를 반환합니다.
* 의존성 주입은 생성자 주입(`@RequiredArgsConstructor`)을 권장합니다.
