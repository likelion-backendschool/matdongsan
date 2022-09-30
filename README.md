# 맛동산 프로젝트

`멋쟁이사자처럼 백엔드스쿨 1기 해커톤 프로젝트`를 통해 기획하고, 개발되었습니다.

## 개발자

### [가준영](https://github.com/Jwhyee)
- 프로젝트 팀장
- `Spring Security`를 이용한 `Account`, `Member` 회원 도메인 개발
- `Profile` 도메인 개발
- `Post`, `Place`, `Reply`, `Likes` 등 전체적인 기능 보조 개발

### [남의영](https://github.com/young0264)
- `Reply`, `Like` 도메인 개발
- 댓글과 관련된 `CRUD` 기능 개발
- 댓글 관련 비동기 방식(JavaScript Ajax) 개발

### [최수용](https://github.com/Choisooyoung98)
- `Favorite` 북마크 도메인 개발
- 북마크 폴더와 관련된 `CRUD` 기능 개발
- `Place`와 연관된 북마크 기능을 비동기 방식(JavaScript Ajax) 개발 

### [전병찬](https://github.com/icoo08217)
- `Post` 도메인 개발
- 맛집 리뷰와 관련된 `CRUD` 기능 개발

### [왕종휘](https://github.com/woowang789)
- `Place` 도메인 개발
- `Kakao Map Api`를 활용하여 `Place` 기능 개발
- `AWS`, `Docker`, `Github Actions` 등 배포와 관련된 `CI/CD` 환경 개발

# 맛집 저장 웹사이트

맛동산 프로젝트는 나만의 맛집을 한 곳에 저장할 수 있는 웹앱입니다.

네이버, 카카오, 인스타그램 등 흩어져있는 나만의 맛집을 한 곳에 모아 저장할 수 있는 페이지입니다.

## 스크린샷

![맛집 등록 페이지](docs/screenshot-1.png)

맛집을 등록하는 페이지

![맛집 포스팅 페이지](docs/screenshot-2.png)

내가 등록한 리뷰를 맛집 지도로 볼 수 있는 페이지

## 왜 이 프로젝트를 만들었는가?

우리는 보통 맛집을 여러 SNS에서 접하고 방문하게 됩니다.

이런 맛집들을 네이버, 카카오 등에 저장할 수 있지만, 막상 찾을 때 내가 어디서 이 맛집을 찾았는지 헷갈리곤 합니다.

이러한 문제점을 해결해보고자 맛집을 한 곳에서 볼 수 있는 서비스를 기획하게 되었습니다.

## 실행하기 위해서 필요한 것

Chromium 기반의 웹에서 모두 작동하며, 위치 기반의 서비스를 사용합니다.

## Tech Stack & Library

### Teck Stack
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Thymeleaf](https://www.thymeleaf.org/)
- [MySQL](https://www.mysql.com/)

### Library
- [jQuery v3.5.1](https://jquery.com/)
- [Kakao Map API](https://apis.map.kakao.com/web/sample/)
- [Lombok](https://projectlombok.org/download)
- [queryDsl](http://querydsl.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

### DevOps
- [AWS EC2](https://aws.amazon.com/ko/ec2/)
- [AWS RDS](https://aws.amazon.com/ko/rds/)
- [Docker](https://www.docker.com/)
- [GitHub Actions](https://github.com/features/actions)

## 앞으로 진행될 사항들

- AWS S3 도입 ➡️ 이미지처리
- 클린 코드를 위한 코드 리팩터링