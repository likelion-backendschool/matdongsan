package com.matdongsan.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {
    TITLE ("title" , "제목"),
    CONTENT("content" , "내용") ,
    AUTHOR("author" , "글쓴이");

    private final String key;
    private final String value;

}

// 이넘타입을 받아서 string 으로 만들어주는 converter