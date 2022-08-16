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

### 2. DB Connection 과정
> 1. 애플리케이션 로직에 의해 `DriverManger`가 DB 드라이버를 조회해 커넥션을 조회한다.
> 2. DB 드라이버는 DB와 `TCP/IP` 커넥션을 연결한다. 이 과정에서 `3 way handshake` 같은 네트워크 동작이 발생한다.
> 3. DB 드라이버는 `TCP/IP` 커넥션이 연결되면 ID,PW 부가 정보를 DB에 전달한다.
> 4. DB는 전달된 정보를 통해 내부 인증을 완료하고, 내부에 **DB세션**을 생성한다.
> 5. DB는 커넥션 생성이 완료되었다는 응답을 보낸다.
> 6. DB드라이버는 커넥션 객체를 생성해 애플리케이션 로직에 반환한다. <br>

> 커넥션을 새로 만드는 과정은 복잡하고 시간이 많이 소요된다. DB는 물론이고, 서버에도 `TCP/IP` 커넥션을 생성하기 위한 리소스를 매번 사용해야 한다.결과적으로 응답속도에 영향을 준다.
> 그렇기 때문에 스프링은 `default`로 `HikariCP`라는 커넥션 풀을 사용해 앱이 실행할 때 병렬적으로 커넥션을 10개 만들고 관리한다.

### 3. 프로젝트 개발을 위한 개인 공부
> `fetch = FetchType.EAGER`
> - 연관된 엔티티를 즉시 조회한다. hibernate는 가능하면 SQL 조인을 사용해서 한번에 조회함.
> - `@ManyToOne(fetch = FetchType.EAGER)`
> - JPA 구현체는 즉시 로딩을 최적화 하기 위해 조인 쿼리를 사용.
> - 2개 이상의 컬렉션을 하나 이상 즉시 로딩은 권장 X
> - `EAGER`는 외부조인을 사용한다(내부 조인이 성능 최적화에 훨씬 유리)
> 1. `fetch = FetchType.LAZY`
>   - 연관된 엔티티를 프록시로 조회. 프록시를 실제 사용할 때에 초기화하며 데이터베이스를 조회한다.
>   - `@ManyToOne(fetch = FetchType.LAZY)`
> 2. JPA 기본 페치 전략을 이렇다.
>   - `@ManyToOne` , `@OneToOne` : 즉시 로딩 `(optional = false : 내부 조인)`
>   - `@OneToMany` , `@ManyToMany` : 지연로딩 `(optional = false : 외부 조인)`
>   - 모든 연관관계는 지연로딩 사용을 권장한다.

### 4. Git-Flow
> 1. git 협업을 인원수만큼 브랜치를 분리해서 하는게 아니라 하나의 브랜치에서 여러명이 접근하여 작업하고 마스터에 옮겨준다.
> 2. Spring Boot는 `@GetMapping`에서 객체를 알아서 받아준다.
> 3. Spring Boot JPA는 직접 JDBC에 접근하지 않는다.
> 4. `Optional`은 알아서 null 처리를 해주어 코드가 지저분해지는 것을 막아준다.
> 5. `@Transactional`은 테스트를 짤 때 테스트마다 `rollback`을 해주어 `BeforeEach`, `AfterEach`를 안해줘도 된다. 즉 영속성처리를 해준다.

## 개발 과정에서 나왔던 질문 (최소 200자 이상)
### 1. 커스텀 어노테이션을 사용하지 않고 인증된 유저를 불러올 효율적인 방법이 있을까요?
> 현재 프로젝트의 문제점은 모든 `View`에서 1개의 `fragments.html`을 불러와 사용했다.<br>
> 또한, 직접 커스텀 어노테이션을 만들어 `Controller`의 모든 메소드마다 이 어노테이션을 이용해 로그인 한 유저를 가져왔어야 했다.<br>
> 즉, 커스텀 어노테이션을 활용해 `View`에 로그인된 유저 정보를 보내줘야지 `header`에 로그인한 유저 닉네임을 띄워줄 수 있었다. 이런 문제점을 해결해보고자 이명한 멘토님에게 질문을 하여 답을 얻었다.<br>
> 이 문제의 답은 `layout`의 이용과 `Spring Security`에 포함되어 있는 `Principal`을 사용하는 것이다.<br>
> 그러면 `fragments.html`에서만 `head`, `footer` 등등을 관리하는 것이 아닌 분리하여 관리할 수 있다.<br>
> 또한, `Principal`을 이용해 현재 로그인한 유저의 아이디를 가져와 필요한 부분만 `VO`에 담아 해결할 수 있었다.<br>

## WBS 추가 내용
- 아직 크게 개발이 된 도메인이 없습니다.
- 오늘부터 본격적인 개발이 진행될 예정이라 다음주부터 더 자세히 기록하겠습니다.

## 개발 결과물 공유
Github Repository URL : [맛동산](https://github.com/likelion-backendschool/matdongsan)