# Kotlin-Backend Meet Up 예제

## 프로젝트 소개

Ktor 프레임워크와 Exposed를 사용하여 간단한 REST API 서버를 구현하는 예제입니다.  
프로젝트는 Gradle과 Kotlin DSL로 구성되어 있고 Java 21 버전, Kotlin 2.0.0 버전, Ktor Framework는 3.0.3 버전을 사용합니다.  
실습 자료는 Branch로 구분되어 있으며, 각 Branch에는 실습 단계별로 구현된 코드가 포함되어 있습니다.

## Spring Cloud Gateway와 Ktor 비교

| 기준                    | Spring Cloud Gateway | Ktor (netty)    | Ktor (CIO)      |
|-----------------------|----------------------|-----------------|-----------------|
| 부팅 시간                 | 2.99 s               | 132 ms          | 147 ms          |
| 유휴 메모리                | 41 MB                | 36.5 MB         | 28.9 MB         |
| **워밍업 부하 30초**        |                      |                 |                 |
| 워밍업 시 최대 메모리 사용량      | 171 MB               | 165 MB          | 158 MB          |
| 워밍업 시 최대 CPU 사용량      | 41.7 %               | 59.1 %          | 58.8 %          |
| **부하 테스트 30초**        |                      |                 |                 |
| 부하 중 최대 메모리 사용량       | 169 MB               | 163 MB          | 161 MB          |
| 부하 중 최대 CPU 사용량       | 12.9 %               | 28.8 %          | 35.8 %          |
| 평균 지연 시간 (스레드 통계)     | 13.70 ms             | 11.94 ms        | 11.71 ms        |
| 평균 요청 처리량 (스레드 통계)    | 823.58               | 920             | 890             |
| 총 처리된 요청 수            | 49,248               | 55,128          | 53,204          |
| 총 요청 처리량              | 1,637.88             | 1,834.00        | 1,768.10        |
| **부하 테스트 60초**        |                      |                 |                 |
| 부하 중 최대 메모리 사용량       | 175 MB               | 170 MB          | 168 MB          |
| 부하 중 최대 CPU 사용량       | 14.5 %               | 29.2 %          | 36.1 %          |
| 평균 지연 시간 (스레드 통계)     | 10.55 ms             | 9.59 ms         | 9.24 ms         |
| 최대 지연 시간 (스레드 통계)     | 138.71 ms            | 158.43 ms       | 86.47 ms        |
| 지연 시간 표준 편차 (스레드 통계)  | 9.85ms (92.28%)      | 7.93ms (92.80%) | 4.75ms (82.51%) |
| 평균 요청 처리량 (스레드 통계)    | 1.09k                | 1.14k           | 1.12k           |
| 최대 요청 처리량 (스레드 통계)    | 1.97k                | 1.78k           | 1.45k           |
| 요청 처리량 표준 편차 (스레드 통계) | 367.74 (64.50%)      | 270.88 (71.25%) | 147.52 (66.44%) |
| 총 처리된 요청 수            | 130,276              | 136,892         | 133,332         |
| 총 요청 처리량              | 2,168.71             | 2,278.19        | 2,221.18        |

## Dependency

intellij IDEA에서 프로젝트 생성시 추가 하는 dependency

1. Status Pages
2. Routing
3. Call Logging
4. Contents Negotiation
5. Jackson
6. Kotlinx Serialization
7. Exposed
8. Koin

### 이후 추가할 dependency

1. ktor-swagger-ui
2. jackson-datatype-jsr310
3. exposed-java-time

## Ktor 소개

- JetBrains에서 지원해주는 Kotlin 기반의 경량 프레임워크

### 장점

- Plugin 방식으로 필요한 부분만 유연하게 확장 가능
- 빠른 Application 구동 속도와 좋은 성능, 높은 처리량
- KotlinDSL 기반에 직관적인 문법으로 설정이 가능
- Coroutine 기반으로 Network I/O Level부터 NonBlocking하게 동작

### 단점

- 높은 CPU 사용량
- 커뮤니티 규모 작고 생태계가 부족
- 기본적으로 제공되는 기능이 적어 플러그인 추가가 필수적
- 직접 구현해줘야 하는 부분이 많아 초기 설정에 대한 리소스가 필요

## 프로젝트 로드맵

- IntelliJ IDEA, Ktor Initializer에서 Ktor 프로젝트 생성
- application.conf file 설정
- Exception Handler 설정
- Contents Negotiation 및 Serialization 설정
- Routing 설정
- Database 설정
- Exposed Entity, Repository 구현
- Dependency Injection 설정
- Swagger 설정

### 요구사항 명세

1. Home API
   - 서버가 정상적으로 동작하는지 확인하는 API
   - GET Method로 호출 시 `Hello, Ktor`를 반환
2. User API
   - 사용자 정보를 관리하는 API로 데이터를 저장하고 조회
   - POST Method로 호출 시 사용자 정보를 저장
   - GET Method로 호출 시 사용자 정보를 조회
     - 단일 사용자 조회와 전체 사용자 조회 구현
       - 단일 사용자 조회 시, PathVariable로 사용자 ID를 받아 조회
       - 전체 사용자 조회 시, 활성화된 상태인 사용자만 대상으로 하며, 이름은 대소문자를 구분하지 않고 포함 여부를 검색
   - Swagger를 사용하여 API 문서화

## 프로젝트 로드맵 상세

### Chapter0. IntelliJ IDEA에서 Ktor 프로젝트 생성

- IntelliJ IDEA에서 Ktor 프로젝트를 선택하고 프로젝트를 생성
- Plugin 추가 시 `Routing, Call Logging, Contents Negotiation, Jackson, Kotlinx Serialization, Exposed, Koin` 추가
- gradle.properties와 build.gradle.kts에서 kotlin version 2.0.0, exposed 0.55.0으로 수정

### Chapter1. application.conf file 설정 및 설명

- application.conf 파일에 `Database` 설정 추가
- Port Default 값은 8080으로 설정
- Database는 `H2`를 사용

### Chapter2. Exception Handler 설정

- Exception.kt 파일을 별도로 분리
- Custom Exception을 별도로 정의
- `StatusPages`를 사용하여 `Exception Handler`를 설정

### Chapter3. Contents Negotiation 및 Serialization 설정

- `jackson-datatype-jsr310` dependency 추가
- Contents Negotiation 설정에서 `Jackson`, `Kotlinx Serialization`, `Java Time` 설정
- 직렬화 과정에서 매핑되지 않은 필드를 무시하도록 설정
- 배열에 단일 값을 바인딩 가능하도록 설정 
- Request Body에 `날짜 객체`와 `Value Class` Binding 설정 추가
- Application Attribute Context에 설정된 ObjectMapper를 등록(설정한 ObjectMapper를 전역에서 사용 가능)

### Chapter4. Routing 설정

- Call Logging 설명
- User Router 별도로 분리
- Get, Post API를 정의(Home, User, UserV2)
- Request Model, Response Model 정의
- `Query Parameter`, `PathVariable`, `Request Body` 정의
- `PathVariable`를 읽어오는 부분을 확장 함수로 리팩토링
- `Request Parameter`를 Model로 Binding 할 수 있게 확장 함수로 리팩토링

### Chapter5. Database 설정

- `application.conf` 파일에 정의된 Database 설정을 읽어와서 `Data Class`에 Binding

### Chapter6. Exposed Schema, Table 설정 및 Repository 구현

- `exposed-java-time` dependency 추가
- BaseTable을 정의하고 `createdAt`, `updatedAt` 컬럼을 추가, 정렬 기능 추가
- Exposed의 `DSL로 Schema, Table` 설정
- Exposed DSL Query를 사용하여 Repository를 구현(CRUD)
- `SchemaUtils`로 Runtime에 Table을 생성

### Chapter7. Koin 설정

- Dependency Injection 설정(Spring의 수동 빈 주입과 유사)
- `factory`, `primary`, `named`를 사용한 qualifier 설명
- Koin을 사용해서 Router에 Repository를 주입
- Primary Bean과 Named Bean을 사용하여 RepositoryV2 주입

### Chapter8. Swagger 설정

- `ktor-swagger-ui` dependency 추가
- Swagger 설정을 위한 `OpenAPI` 설정
- `SwaggerUI`를 사용하여 API 문서화

## 프로젝트 실행

- IntelliJ IDEA에서 프로젝트를 실행
- `http://localhost:8080`으로 접속하고 Swagger UI로 API 문서 확인 및 테스트