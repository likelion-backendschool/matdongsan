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

### 1. 1회차(2022.08.16) Slack 허들 진행 (전체 참여)
> **회의 안건** : 템플릿 사용법 및 어노테이션 사용 변경
- 기존에는 1개의 `fragments.html`에서 `head`, `header`, `footer` 등을 관리
  - 변경 사항 : `layout`을 만들어 `React Component`와 같이 변경
- 기존 로그인한 유저를 가져오기 위해 커스텀 어노테이션을 사용하였음
  - 변경 사항 : `Spring Security Principal`을 사용해 인증된 `SevurityUser`를 가져옴 
## 현재까지 개발 과정 요약 (최소 500자 이상)
### 1. `Spring Boot`와 `Thymeleaf`의 연동
> `Spring Boot`를 공부할 때 `Controller`에서 **객체**를 `Model`에 담아 `View`에 뿌려줄 때<br>
> `Thymeleaf`에서는 이를 인식하고 사용할 수 있도록 `Hint`를 보여준다.<br>
> 하지만 어떤 문제 때문인지 정확히 `Model`을 통해 넘겨주어도 빨간불이 뜨는 상황이 생겨 찾아보았다.<br>
> 해당 [링크](https://www.inflearn.com/questions/561794) 에서 확인할 수 있듯이 `Spring Boot Version`이 `2.7.x`부터는 인식이 안 된다는 것을 확인했다.<br>
> 이를 인지하고 현재 `Version`을 확인해보니 `2.7.2`인 것을 확인하였다. 때문에 이를 `2.6.3`으로 다운그레이드하여 해결하였다.<br>
> 💡 이 과정을 통해 `Spring Boot` 버전마다 지원하는 것에 대한 차이가 크다는 것을 알게 되었다.

## 개발 과정에서 나왔던 질문 (최소 200자 이상)
### 1. 커스텀 어노테이션을 사용하지 않고 인증된 유저를 불러올 효율적인 방법이 있을까요?
> 현재 프로젝트의 문제점은 모든 `View`에서 1개의 `fragments.html`을 불러와 사용했다.<br>
> 또한, 직접 커스텀 어노테이션을 만들어 `Controller`의 모든 메소드마다 이 어노테이션을 이용해 로그인 한 유저를 가져왔어야 했다.<br>
> 즉, 커스텀 어노테이션을 활용해 `View`에 로그인된 유저 정보를 보내줘야지 `header`에 로그인한 유저 닉네임을 띄워줄 수 있었다. 이런 문제점을 해결해보고자 이명한 멘토님에게 질문을 하여 답을 얻었다.<br>
> 이 문제의 답은 `layout`의 이용과 `Spring Security`에 포함되어 있는 `Principal`을 사용하는 것이다.<br>
> 그러면 `fragments.html`에서만 `head`, `footer` 등등을 관리하는 것이 아닌 분리하여 관리할 수 있다.<br>
> 또한, `Principal`을 이용해 현재 로그인한 유저의 아이디를 가져와 필요한 부분만 `VO`에 담아 해결할 수 있었다.<br>

## 개발 결과물 공유
Github Repository URL : [맛동산](https://github.com/likelion-backendschool/matdongsan)