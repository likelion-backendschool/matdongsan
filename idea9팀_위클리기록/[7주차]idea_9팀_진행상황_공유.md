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

### 금주의 회의는 따로 진행하지 않았습니다.

## 현재까지 개발 과정 요약 (최소 500자 이상)

<!-- ### Account & Member
>  -->

### Account
- 회원 탈퇴 시 비밀번호 인증 후 탈퇴 추가
- 소개글 변경 기능 추가

### > Reply
- 추천 비동기 처리
- 댓글 등록 비동기 처리

### Post
- 글 제목 검색 기능 추가

### Favorite
- 북마크 폴더 이름 중복 불가 구현

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

### Account

> 모든 페이지의 `header`에 `로그인 ID`가 아닌 `nickname`으로 띄우고 싶은데 방법이 없는지 궁금합니다!

```java
<span class="media-body">
		<span class="item-title" sec:authentication="principal.username">로그인ID</span>
</span>
```
-> SecurityUser에 사용자 정의 필드 추가로 해결

## WBS 추가 내용
- 메인페이지 디자인
- 프로필 페이지 수정
- 댓글 및 좋아요 기능 추가

## 개발 결과물 공유
Github Repository URL : [맛동산](https://github.com/likelion-backendschool/matdongsan/tree/develop)
