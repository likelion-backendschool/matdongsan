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

### 1. 6회차(2022.09.05) 디스코드 진행 (전체 참여)
> **회의 안건** : 개발 완료 시기 확정
- 각자 개발을 완료할 시기를 구체적으로 정한 뒤 개발 이어서 진행

## 현재까지 개발 과정 요약 (최소 500자 이상)

<!-- ### Account & Member
>  -->

### Account
- Kakao 로그인 구현
- 프로필 뷰 추가
- 닉네임 변경 및 비밀번호 변경 기능 추가

### > Reply
- 댓글 수정,취소기능 구현
- 댓글 등록/수정날짜 구현
- 댓글 좋아요기능 구현중> 내 위치 좌표를 불러오는 브라우저API를 통해 내 주변 음식점을 카드 뷰로 볼 수 있는 페이지를 만들었습니다. 

### Post
- 게시글 등록 폼 valid 구현
- 게시글 상세 페이지 구현(미완)

### Favorite
- Bookmark 도메인과 연결해서 사용하려고 했으나 단점이 많은걸 알게되어 도메인 수정을 하는중
- bookmark 도메인을 삭제하고 favorite 하나로 해결하려고 진행중

<!-- ### Favorite
>  -->

## 개발 과정에서 나왔던 질문 (최소 200자 이상)
### Reply
> 댓글 수정을 구현하면서 이슈가 있었습니다. 댓글 수정버튼을 누르고 넘어간 페이지에서 수정하기 전의 comment가 넘어갔어야 했는데 넘어가지 않아 에러가 발생했었습니다.  그 페이지가 post단에서 GetMapping으로 열리는 페이지였기 때문에 post쪽이아닌 get쪽에서 comment값을 넘기는 dto객체를 model에 넣어주어야 값이 넘어감을 배웠습니다.

### Image
> 이미지 업로드 폼에서 ‘사용자가 올린 이미지를 어떻게 보여줘야 하나’ 고민했습니다. 처음 떠올린 방법은 다음과 같습니다.
사용자가 이미지 선택 -> 서버에 전송 -> 서버가 임시폴더에 저장 ->  브라우저가 임시폴더의 이미지를 요청
하지만 이 방법은 서버에 전송하고 가져오기까지 동기적으로 수행되어야 되기 때문에 사용자 경험이 좋지 않고, 임시폴더를 관리해야 한다는 단점이 있습니다.
서버를 거치지 않고 해결하는 방법을 구글링하다  FileReader 를 이용하는 방법을 찾았습니다.
```javascript
// 이미지 파일 각각 콜백 함수 등록

$.each(event.target.files,function(i,j){
		//FileReader객체 생성
    let reader = new FileReader();
 
		// FileReader 객체가 로드되었 떄 수행할 핸들러 등록
    reader.onload = function(event) { 

				// img element를 만들어 세팅하고 DOM에 집어넣음
		    let img = document.createElement("img");
		    img.setAttribute("src", event.target.result);
		    imgContainer.appendChild(img);
		};
		reader.readAsDataURL(j);
```

## WBS 추가 내용
- 메인페이지 디자인 구체화
- 프로필 페이지 기능 구현

## 개발 결과물 공유
Github Repository URL : [맛동산](https://github.com/likelion-backendschool/matdongsan/tree/develop)
