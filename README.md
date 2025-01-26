# Kotlin-Backend Meet Up 예제

## 1. 프로젝트 소개

Ktor 프레임워크를 사용하여 간단한 REST API 서버를 구현하는 예제입니다.
ORM은 Exposed를 사용합니다.  
프로젝트는 Gradle과 Kotlin DSL로 구성되어 있고 JDK 17, Kotlin 1.9.13 버전을 사용합니다.  


## 2. Dependency

```text
intellij IDEA에서 프로젝트 생성시 추가 하는 dependency {
    Kotlin,
    Ktor,
    Logback,
    JUnit5,
    Ktor-Server-Netty,
    
    ---
    
    Double Request,
    Request Validation,
    Routing,
    Swagger,
    Call Logging,
    Contents negation,
    Jackson,
    Kotlinx Serialization,
    Exposed,
    Koin
}

별도로 추가 하는 dependency {
    H2,
    HikariCP,
    Kotlin Logging
}
```

## 3. 프로젝트 로드맵

1. IntelliJ IDEA에서 Ktor 프로젝트 생성
1. Dependency 추가
1. application.conf file 설정 및 설명
1. Swagger 설정
1. Double Request 설정(Double Request는 무엇이고 왜 필요한가를 설명)
1. Request Validation 설정
1. Call Logging 설정(Kotlin Logging 사용)
1. Contents negation 및 Serialization 설정 (Jackson, Kotlinx Serialization, ZonedDateTime)
1. Routing 설정(Spring과 어떻게 다른지)
1. Koin 설정(이것도 Spring과 어떻게 다른지)
1. Exposed 설정
1. H2 Database 설정
1. HikariCP 설정
1. Entity 및 Repository 구현
1. Controller와 Service 구현
1. Request Parameter를 확장 함수로 만들기, Path variable 별도의 클래스로 만들어서 관리하기
1. Test 코드 작성


## 4. 프로젝트 로드맵 상세

### 1. IntelliJ IDEA에서 Ktor 프로젝트 생성

1. IntelliJ IDEA에서 새로운 프로젝트를 생성합니다.
1. 생성 할 때 Ktor 프로젝트를 선택합니다.
1. Plugin을 추가 할 때 Dependency에 Double Request, Request Validation, Routing, Swagger, Call Logging, Contents negation, Jackson, Kotlinx Serialization, Exposed, Koin을 추가합니다.

### 2. Dependency 추가

1. build.gradle.kts 파일에 dependency를 추가합니다.
1. 추가한 dependency는 runtimeOnly에 H2, implementation에 HikariCP, Kotlin Logging를 추가합니다.

### 3. application.conf file 설정 및 설명

1. application.conf 파일을 생성합니다.
1. application.conf 파일에는 포트 설정, Swagger 설정, Database 설정을 추가합니다.
1. 포트 설정은 8080으로 설정합니다.
1. Swagger 설정은 /swagger로 설정합니다.
1. Database 설정은 H2 Database를 사용하고, HikariCP를 사용하여 설정합니다.


### 4. Swagger 설정

1. Swagger 설정은 SwaggerUI를 사용하여 설정합니다.

### 5. Double Request 설정

1. Double Request를 설정할 때 Double Request는 무엇이고 왜 필요한가를 설명합니다.

### 6. Request Validation 설정

1. Request Validation을 설정할 때 Request Validation은 무엇이고 왜 필요한가를 설명합니다.

### 7. Call Logging 설정

1. Call Logging을 설정할 때 Call Logging은 무엇이고 왜 필요한가를 설명합니다.
1. 추가적으로 Kotlin Logging과 Logback을 사용하여 로깅을 설정합니다.
1. Call Logging은 Request와 Response를 로깅합니다.

### 8. Contents negation 및 Serialization 설정

1. Contents negation은 무엇이고 왜 필요한가를 설명합니다.
1. Serialization은 Jackson, Kotlinx Serialization을 사용하여 설정합니다.
1. Body에 ZonedDateTime을 바인딩하는 방법을 설명합니다.

### 9. Routing 설정

1. Routing 설정은 Spring과 어떻게 다른지를 설명합니다.
1. Routing 설정은 Swagger와 어떻게 연동되는지를 설명합니다.
1. Routing에서 Path variable를 사용하는 방법을 설명합니다.
1. Routing에서 Query parameter를 사용하는 방법을 설명합니다.

### 10. Koin 설정

1. Koin이 무엇이고 Dependency Injection을 어떻게 하는지를 설명합니다.
1. 설명 할 때 Spring과 어떻게 다른지를 설명합니다.

### 11. Exposed 설정

1. Exposed가 무엇이고 ORM을 어떻게 사용하는지를 설명합니다.
1. 간단하게 Entity와 Schema를 만들고 Repository를 만들어서 사용하는 방법을 설명합니다.
1. 간단하게 DAO와 DSL을 사용하는 방법을 설명합니다.

### 12. H2 Database 설정

1. Database 설정은 H2 Database를 사용합니다.
1. conf 파일에 Database 설정을 추가합니다.
1. 설정 파일을 읽어오는 방법을 설명합니다.
1. H2 Database를 사용할 때 HikariCP를 사용하여 설정합니다.
1. transaction과 connection pool을 설정합니다.

### 13. Entity 및 Repository 구현

1. Entity와 Repository를 구현합니다.
1. Entity는 Exposed의 Table을 상속 받아서 구현합니다.
1. BaseTable을 만들고 적용하고 Update 시간을 자동으로 설정하는 방법을 설명합니다.
1. Repository는 Entity를 사용하여 CRUD를 구현합니다.

### 14. Controller와 Service 구현

1. Controller와 Service를 구현합니다.
1. Controller는 Routing을 사용하여 구현합니다.
1. Service는 Repository를 사용하여 구현합니다.

### 15. Request Parameter를 확장 함수로 만들기, Path variable 별도의 클래스로 만들어서 관리하기

1. Request Parameter를 확장 함수로 만들어서 사용하는 방법을 설명합니다.
1. Path variable을 별도의 클래스로 만들어서 사용하는 방법을 설명합니다.


### 16. Test 코드 작성

1. JUnit5를 사용하여 Test 코드를 작성합니다.
1. Test 코드는 Controller, Service, Repository를 작성합니다.

## 5. 프로젝트 실행

1. IntelliJ IDEA에서 프로젝트를 실행합니다.
2. Swagger로 API를 확인하고 실행합니다.