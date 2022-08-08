## 팀 구성원, 개인 별 역할
### 가준영(팀장)
> 각 회원들을 관리하는 `Member Domain` 개발 <br/>
### 남의영
> 등록된 맛집에 댓글을 달 수 있는 `Reply Domain` 개발 <br/>
### 왕종휘
> 허위 글과 댓글을 신고하는 `Report`, 맛집에 좋아요를 누를 수 있는 기능인 `Likes Domain` 개발 <br/>
### 전병찬
> 맛집을 등록할 수 있는 `Post Domain` 개발 <br/>
### 최수용
> 등록되어 있는 맛집들 중 마음에 드는 맛집을 저장할 수 있는 `Favorite Domain` 개발 <br/>
## 팀 내부 회의 진행 회차 및 일자

### 1. 1회차(2022.08.09) Slack 허들 진행 (전체 참여)
> **회의 안건** : 템플릿 사용법 및 프로젝트 개발 안내  
- 모두가 `Spring Boot` 프로젝트를 많이 진행해보지 않았기 때문에 아래와 같은 순서로 리팩토링한다.
  1. 우선 기능만 잘 동작하도록 무지성 개발
  2. 매주 코드리뷰를 통하여 리팩터링
  3. 추후 작업 진행
- 프로젝트 개발을 진행할 때에는 `Agile Pair Programming`을 진행
  - 장마와 코로나 등의 이슈로 대면 짝 프로그래밍을 진행하지 못하는 관계로 Discord 혹은 Slack 화면 공유를 하며 짝 프로그래밍을 진행
## 현재까지 개발 과정 요약 (최소 500자 이상)
### 1. `Spring Boot`와 `Thymeleaf`의 연동
> `Spring Boot`를 공부할 때 `Controller`에서 **객체**를 `Model`에 담아 `View`에 뿌려줄 때<br>
> `Thymeleaf`에서는 이를 인식하고 사용할 수 있도록 `Hint`를 보여준다.<br>
> 하지만 어떤 문제 때문인지 정확히 `Model`을 통해 넘겨주어도 빨간불이 뜨는 상황이 생겨 찾아보았다.<br>
> 해당 [링크](https://www.inflearn.com/questions/561794) 에서 확인할 수 있듯이 `Spring Boot Version`이 `2.7.x`부터는 인식이 안 된다는 것을 확인했다.<br>
> 이를 인지하고 현재 `Version`을 확인해보니 `2.7.2`인 것을 확인하였다. 때문에 이를 `2.6.3`으로 다운그레이드하여 해결하였다.<br>
> 💡 이 과정을 통해 `Spring Boot` 버전마다 지원하는 것에 대한 차이가 크다는 것을 알게 되었다.

## 개발 과정에서 나왔던 질문 (최소 200자 이상)
### 1. 아직 없음
> 아직 없음

## 개발 결과물 공유
Github Repository URL : [맛동산](https://github.com/likelion-backendschool/matdongsan)