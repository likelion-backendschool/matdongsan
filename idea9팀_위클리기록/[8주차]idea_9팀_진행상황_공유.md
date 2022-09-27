## 팀 구성원, 개인 별 역할
### 가준영(팀장)
> 각 회원들을 관리하는 `Member Domain` 개발 <br/>
### 남의영
> 등록된 맛집에 댓글을 달 수 있는 `Reply Domain` 개발 <br/>
### 전병찬
> 맛집을 등록할 수 있는 `Post Domain` 개발 <br/>
### 최수용
> 등록되어 있는 맛집들 중 마음에 드는 맛집을 저장할 수 있는 `Favorite Domain` 개발 <br/>
## 팀 내부 회의 진행 회차 및 일자

### 금주의 회의는 따로 진행하지 않았습니다.

## 현재까지 개발 과정 요약 (최소 500자 이상)

### Account
- 회원 가입 완료
- 프로필 페이지 완료

### Reply
- 댓글 방식 비동기 처리 완료

### Post
- 게시글 검색 기능 완료

### Favorite
- 기능 개발 완료

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

### Reply

> 댓글 수정을 구현하면서 이슈가 있었습니다. 댓글 수정버튼을 누르고 넘어간 페이지에서 수정하기 전의 comment가 넘어갔어야 했는데 넘어가지 않아 에러가 발생했었습니다.  그 페이지가 post단에서 GetMapping으로 열리는 페이지였기 때문에 post쪽이아닌 get쪽에서 comment값을 넘기는 dto객체를 model에 넣어주어야 값이 넘어감을 배웠습니다.

> model에 dto라는 이름으로 데이터를 넘겨 주었는데 네이밍 규칙 때문이지 데이터가 넘어가지 못하는 경우가 있었다. 네이밍에도 신경을 써야할 부분인 것 같다.

## WBS 추가 내용
- 검색 기능 추가

## 개발 결과물 공유
Github Repository URL : [맛동산](https://github.com/likelion-backendschool/matdongsan/tree/develop)
