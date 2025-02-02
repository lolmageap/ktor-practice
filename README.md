# Kotlin-Backend Meet Up 예제

## - 프로젝트 소개

Ktor 프레임워크를 사용하여 간단한 REST API 서버를 구현하는 예제입니다.  
ORM은 Exposed를 사용합니다.  
프로젝트는 Gradle과 Kotlin DSL로 구성되어 있고 JDK 17, Kotlin 2.0.0 버전, Ktor Framework는 3.0.3 버전을 사용합니다.

## - Dependency

```text
intellij IDEA에서 프로젝트 생성시 추가 하는 dependency {
    Kotlin,
    Ktor,
    Logback,
    JUnit5,
    Ktor-Server-CIO,
    
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

## - 프로젝트 로드맵

- PPT로 Ktor에 대한 간단한 소개(어떤 장점으로 인해 Ktor를 사용하는지)
- IntelliJ IDEA에서 Ktor 프로젝트 생성
- Dependency 추가 및 변경(gradle.kts와 gradle.properties 파일에 kotlin version, exposed version 변경)
- application.conf file 설정 및 설명
- Exception Handler 설정
- Contents negation 및 Serialization 설정 (Jackson, Kotlinx Serialization, ZonedDateTime)
- Call Logging을 plugin으로 만들어서 설정
- Routing 설정(Spring과 어떻게 다른지)
- Request Parameter를 확장 함수로 만들기, Path variable 별도의 클래스로 만들어서 관리하기
- Koin 설정(이것도 Spring과 어떻게 다른지)
- Database 설정
- Exposed 설정
- Entity 및 Repository 구현(event subscribe로 updated at 구현하기)
- Controller와 Service 구현(간단한 post와 get API만 구현)

## - 프로젝트 로드맵 상세

### PPT로 Ktor에 대한 간단한 소개

- Ktor는 무엇이고 어떤 장점으로 인해 Ktor를 사용하는지를 설명합니다.
- Ktor는 비동기 처리, 빠른 속도, 경량화, 쉬운 사용성이 장점입니다.
- 단점은 아직까지는 생태계가 부족합니다.

### 0. IntelliJ IDEA에서 Ktor 프로젝트 생성

- IntelliJ IDEA에서 새로운 프로젝트를 생성합니다.
- 생성 할 때 Ktor 프로젝트를 선택합니다.
- Plugin을 추가 할 때 Dependency에 Routing, Call Logging, Contents negation, Jackson, Kotlinx Serialization, Exposed, Koin을 추가합니다.
- gradle.properties와 build.gradle.kts에서 kotlin version 2.0.0, exposed 0.55.0으로 수정합니다.

### 1. application.conf file 설정 및 설명

- application.conf 파일을 생성합니다.
- application.conf 파일에는 포트 설정, Database 설정을 추가합니다.
- 포트 설정은 8080으로 설정합니다.
- Database 설정은 H2 Database를 사용하여 설정합니다.

### 2. Exception Handler 설정

- Status Pages를 사용하여 Exception Handler를 설정합니다.
- Exception.kt File을 별로도 분리합니다.

### 3. Contents negation 및 Serialization 설정

- Contents negation은 무엇이고 왜 필요한가를 설명합니다.
- Serialization은 Jackson, Kotlinx Serialization을 사용하여 설정합니다.
- Body 객체에 ZonedDateTime을 바인딩하는 방법을 설명합니다.

### 4. Routing 설정

- Call Logging을 설정할 때 Call Logging은 무엇이고 왜 필요한가를 설명합니다.
- Routing 설정은 Spring과 어떻게 다른지를 설명합니다.
- Request, Response Model을 만들고 사용하는 방법을 설명합니다.
- Routing에서 Query parameter와 Request Body를 사용하는 방법을 설명합니다.
- Request Parameter를 확장 함수로 만들어서 사용하는 방법을 설명합니다.
- Routing에서 Path variable를 사용하는 방법을 설명합니다.
- Path variable을 별도의 클래스로 만들어서 사용하는 방법을 설명합니다.

### 5. Database 설정

- Database 설정은 H2 Database를 사용합니다.
- conf 파일에 정의된 Database 설정을 읽어오는 방법을 설명합니다.
- Transaction과 Connection Pool을 설정합니다.

### 6. Exposed Schema, Table 설정

- Exposed가 무엇이고 ORM을 어떻게 사용하는지를 설명합니다.
- 간단하게 DAO와 DSL을 사용하는 방법을 설명합니다.
- Entity는 Exposed의 Table을 상속 받아서 구현합니다.
- Update 시간을 자동으로 설정하는 방법을 설명하고 구현합니다.

### 7. Repository 구현

- Exposed DSL Query를 사용하여 Repository를 구현합니다.

### 8. Koin 설정

- Koin이 무엇이고 Dependency Injection을 어떻게 하는지를 설명합니다.
- 설명 할 때 Spring과 어떻게 다른지를 설명합니다.
- Router에서 Koin을 사용하여 Repository를 주입하는 방법을 설명합니다.

## - 프로젝트 실행

- IntelliJ IDEA에서 프로젝트를 실행합니다.
- request.http 파일로 API를 확인하고 실행합니다.