package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

//@AllArgsConstructor : 클래스 안쪽의 모든 필드를 매개변수로 하는 생성자를 자동으로 만들어준다.
@AllArgsConstructor
@ToString
public class ArticleForm {
    private String title; //제목을 받을 필드
    private String content; //내용을 받을 필드

    // 폼 데이터를 담은 DTO 객체를 엔티티로 반환
    // 전달값은 Article 클래스의 생성자 형식에 맞게 작성하면 된다.
    public Article toEntity() {
        return new Article(null, title, content);
    }
}
