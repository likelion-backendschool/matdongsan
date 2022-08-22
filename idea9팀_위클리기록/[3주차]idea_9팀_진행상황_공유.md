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

### 금주 내부 회의는 없었습니다.

## 현재까지 개발 과정 요약 (최소 500자 이상)

### Account & Member
> `DDD(Domain Driven Design)`를 위해 `Security`와 직접적으로 연관이 있는 `Account`를 따로 두고, 게시글 및 댓글과 관련된 내용을 올리는 용도의 `Member`를 구분하였습니다.<br>
> 회원가입 시 `user login id` 중복을 미리 검사하기 위해 `Ajax`를 이용한 중복 검사를 진행하였습니다. 또한 비밀번호 유효성 검사를 위해 우선 **비밀번호 더블 체킹** `script`를 추가하였고, 유효성에 대한 검사도 추가할 예정입니다.

### Reply
> 게시물에  댓글을 달기위해 `Controller`를 기준으로 댓글을 게시물에서 댓글조회, 댓글저장, 수정, 삭제로직을 구현했습니다. 현재 `ajax` `jquery` 를 활용하여 비동기통신으로 댓글을 달고싶어서 찾아보며 공부중에 있습니다.<br>
> `Controller`단의 `Mapping`은 게시글 `View`가 구현이되면 진행이 될 예정인데 아마 그 전까지 게시물의 특정위치에 댓글을 달아보는 연습을 진행해서 익혀볼 생각입니다.

### Place
> 음식점 상세페이지에서 메인 이미지, `Favorite` 수, 메뉴를 추가했습니다. <br>
> 아직 `View`에 반영을 안 했지만 편의시설 여부에 대한 정보를 추가 저장하도록 음식점 저장 로직을 변경했습니다.

### Posts
> `CRUD` 상세 페이지에 사용할 해당 폼을 선택하였고 , 상세 페이지를 구현하고 있습니다. <br>
> 페이지에 알맞는 웹 에디터를 찾아보고 있습니다.

### Favorite
> 북마크 기능 `Favorite` 도메인 추가. 이후 `Favorite` 관리 `View`를 구현할 계획입니다.

## 개발 과정에서 나왔던 질문 (최소 200자 이상)
### JavaScript에서 Thymeleaf Attribute 값은 어떻게 사용할까?
> 음식점 상세보기 페이지에서 `Favorite` 수를 표시하기 위해 `model.attribute()` 로 `favoriteCount`를 추가했습니다.<br>
> 처음에는 `<span th:text="${favoriteCount}">30</span>` 으로 `html`로 렌더링 될 때 값을 정적으로 표시되게끔 했지만 상호작용을 위해 구현을 변경했습니다.<br>
> `favoriteCount`를 `js` 변수로 받아 클릭 이벤트가 발생할 때마다 `element.innerText` 를 최신화하는 방향으로 구현했습니다.

```html
<span id="favoriteCount" >30</span>
```

```javascript
<script th:inline="javascript">
  let favoriteCount= [[${favoriteCount}]];

  let favoriteCountEl=document.getElementById("favoriteCount");
  let bookmarkBtnEl=document.getElementById("bookmarkBtn");

  updateFavoriteContent();

  function updateFavoriteContent(){
  favoriteCountEl.innerText = favoriteCount;
}

  function sendFavorite(){
  return fetch("/api/place/"+placeId+"/favorite",{
  method:"POST",
}).then(async res => {
  var json = await res.json()
  favoriteCount= json['favoriteCount'];

  updateFavoriteContent();
})
}
  bookmarkBtnEl.addEventListener("click",sendFavorite)
</script>
```

## WBS 추가 내용
- 기존 로그인 기능에 대한 리팩터링이 이루어지고 있습니다.
- 게시판 제작 및 기능 구현에 대한 개발이 진행되고 있습니다.
- 댓글 및 좋아요 기능 구현에 대한 개발이 진행되고 있습니다.

## 개발 결과물 공유
Github Repository URL : [맛동산](https://github.com/likelion-backendschool/matdongsan/tree/develop)