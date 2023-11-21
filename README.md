# Front 서버 구현
---
### Developer : 황시준

### Build
> ./gradlew clean build


### Introduction
---
- 개인 프로젝트로 진행중인 Front Server 입니다.  
사용자가 실제로 보게 될 서버입니다.
## Member  
---
    1. 회원 가입
    2. 회원 탈퇴
    3. 회원 수정

## Jenkins
---
    1. 프로젝트 생성
    2. 프로젝트 실행

사용자는 사전에 작성된 thymeleaf 기반의 페이지에 접근하게 되는데 회원가입을 수행하게 되면 API 서버와 REST 통신을 수행합니다.  
로그인을 수행하게 되면 Auth 서버와 REST 통신을 수행하는데 유효한 사용자라면 JWT토큰을 Http Header에 담아 서버에 접근하게 됩니다.

### Skills
---
- Spring Boot
- Spring Security
- RestAPI
- Thymeleaf
- OOP

### 추가할 내용
----
- Apache Kafka