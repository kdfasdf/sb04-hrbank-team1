# HR Bank
- 부제: Batch로 데이터를 관리하는 Open EMS
- 소개: 기업의 인적 자원을 안전하게 관리하는 서비스
- 개발 기간: 2025.7.28~2025.8.6


# 팀원

<br>


<table> <tr> <td align="center" width="150"> <a href="https://github.com/kdfasdf"><b>김대호</b></a><br/> <img src="https://github.com/kdfasdf.png" width="100" style="border-radius: 50%"/><br/> <sub><b>BE</b></sub> </td> <td align="center" width="150"> <a href="https://github.com/chaoskyj1120"><b>강은혁</b></a><br/> <img src="https://github.com/chaoskyj1120.png" width="100" style="border-radius: 50%"/><br/> <sub><b>BE</b></sub> </td> <td align="center" width="150"> <a href="https://github.com/hello13580"><b>정지환</b></a><br/> <img src="https://github.com/hello13580.png" width="100" style="border-radius: 50%"/><br/> <sub><b>BE</b></sub> </td> <td align="center" width="150"> <a href="https://github.com/Eunhye0k"><b>권용진</b></a><br/> <img src="https://github.com/Eunhye0k.png" width="100" style="border-radius: 50%"/><br/> <sub><b>BE</b></sub> </td> <td align="center" width="150"> <a href="https://github.com/joongwonAn"><b>안중원</b></a><br/> <img src="https://github.com/joongwonAn.png" width="100" style="border-radius: 50%"/><br/> <sub><b>BE</b></sub> </td> </tr> </table>

## 기술 스택
- Java 17
- Spring Boot 3.5.4
- Spring Data JPA 3.5.2
- PostgreSQL 42.7.7
- springdoc-openapi 5.20.1
- MapStruct 1.5.5
- Railway.io
- H2 2.3.232

## 팀원 별 구현 기능
### 안중원
- 직원 수 추이 조회
- 직원 분포 조회
- 직원 수 조회
- 파일 다운로드
- 프로필, 백업 데이터 저장

### 강은혁
- 부서 목록 조회
- 부서 등록
- 부서 상세 조회
- 부서 삭제
- 부서 수정

### 권용진
- 직원 목록 조회
- 직원 등록
- 직원 상세 조회
- 직원 수정
- 직원 삭제

### 정지환
- 직원 정보 수정 이력 목록 조회
- 직원 정보 수정 이력 상세 조회
- 수정 이력 건수 조회

### 김대호
- 데이터 백업 목록 조회
- 데이터 백업 생성
- 최근 백업 정보 조회
- 백업 데이터, 에러 로그 저장

## 프로젝트 구조

```
src
├─ main
│  ├─ java
│  │  └─ com
│  │     └─ team1
│  │        └─ hrbank
│  │           ├─ domain
│  │           │  ├─ backup
│  │           │  │  ├─ controller
│  │           │  │  ├─ dto
│  │           │  │  │  └─ response
│  │           │  │  ├─ entity
│  │           │  │  ├─ exception
│  │           │  │  ├─ mapper
│  │           │  │  ├─ repository
│  │           │  │  ├─ scheduler
│  │           │  │  └─ service
│  │           │  ├─ base
│  │           │  ├─ changelog
│  │           │  ├─ department
│  │           │  ├─ employee
│  │           │  └─ file
│  │           ├─ global
│  │           │  ├─ api
│  │           │  ├─ config
│  │           │  ├─ constant
│  │           │  └─ error
│  │           └─ HrbankApplication.java
│  └─ resources
│     ├─ application-dev.yaml
│     ├─ application-prod.yaml
│     ├─ application.yaml
```

### 배포 주소
https://sb04-hrbank-team1-production.up.railway.app/#/dashboard
