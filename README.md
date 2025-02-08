# Kotlin-Backend Meet Up 예제

## - 프로젝트 소개

Ktor 프레임워크와 Exposed를 사용하여 간단한 REST API 서버를 구현하는 예제입니다.  
프로젝트는 Gradle과 Kotlin DSL로 구성되어 있고 Java 21 Version, Kotlin 2.0.0 버전, Ktor Framework는 3.0.3 버전을 사용합니다.  

## - Dependency

```text
intellij IDEA에서 프로젝트 생성시 추가 하는 dependency {
    Kotlin,
    Ktor,
    Logback,
    JUnit5,
    Ktor-Server-CIO
    
    ---
    
    Status Pages,
    Routing,
    Call Logging,
    Contents negation,
    Jackson,
    Kotlinx Serialization,
    Exposed,
    Koin
}
```

## Ktor 소개

- JetBrains에서 지원해주는 Kotlin 기반의 경량 프레임워크
- 장점 : Network I/O Level부터 코루틴 지원, 빠른 속도, 경량화, plugin 방식으로 유연한 확장 가능
- 단점 : 부족한 생태계, 설정에 대한 리소스가 필요

## - 프로젝트 로드맵

- IntelliJ IDEA 혹은 Ktor Initializer에서 Ktor 프로젝트 생성
- Dependency 추가 및 변경(gradle.kts와 gradle.properties 파일에 kotlin version, exposed version 변경)
- application.conf file 설정 및 설명
- Exception Handler 설정
- Contents negation 및 Serialization 설정(Jackson, Kotlinx Serialization, Java Time)
- Call Logging, Routing(Path variable, Request Parameter, Request Body) 설정
- Database 설정
- Exposed Entity, Repository 구현
- Koin 설정

## - 프로젝트 로드맵 상세

### 0. IntelliJ IDEA에서 Ktor 프로젝트 생성

- IntelliJ IDEA에서 Ktor 프로젝트를 선택하고 프로젝트를 생성
- Plugin을 추가 할 때 Dependency에 `Routing, Call Logging, Contents Negation, Jackson, Kotlinx Serialization, Exposed, Koin` 추가
- gradle.properties와 build.gradle.kts에서 kotlin version 2.0.0, exposed 0.55.0으로 수정

### 1. application.conf file 설정 및 설명

- application.conf 파일에 `포트`, `Database` 설정 추가
- 포트 Default 값은 8080으로 설정
- Database는 `H2`를 사용

### 2. Exception Handler 설정

- `StatusPages`를 사용하여 `Exception Handler`를 설정

### 3. Contents negation 및 Serialization 설정

- Contents Negation 설정에서 `Jackson`, `Kotlinx Serialization`을 사용
- Request Body에 `날짜 객체`와 `Value Class` 바인딩 설정 추가

### 4. Routing 설정

- Call Logging 설명
- Get, Post API를 정의(v1, v2)
- Request Model, Response Model 정의
- `Query Parameter`, `PathVariable`, `Request Body` 정의
- `PathVariable`과 `Request Parameter`를 확장 함수로 리팩토링

### 5. Database 설정

- `application.conf` 파일에 정의된 Database 설정을 읽어와서 `Data Class`에 바인딩

### 6. Exposed Schema, Table 설정

- Exposed의 `DSL로 Schema, Table` 설정
- `SchemaUtils`로 Runtime에 Table을 생성

### 7. Repository 구현

- Exposed DSL Query를 사용하여 Repository를 구현

### 8. Koin 설정

- Dependency Injection 설정(Spring의 수동 빈 주입과 유사)
- `factory`, `primary`, `named`를 사용한 qualifier 설정
- Koin을 사용해서 Router에 Repository를 주입

## - 프로젝트 실행

- IntelliJ IDEA에서 프로젝트를 실행
- request.http 파일로 API를 확인하고 실행