package com.example.firstproject.controller;

import com.example.firstproject.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.repository.ArticleRepository;

@Controller
public class ArticleController {
    @Autowired  // 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    // 뷰 페이지에서 폼 데이터를 post 방식으로 전송했으므로 컨트롤러에서 받을 때도 @PostMapping()으로 받는다.
    // @PostMapping("/articles/create") : new.mustache에서 <form> 태그에 action="/articles/create"로 설정했으므로
    @PostMapping("/articles/create")
    // 폼에서 전송한 데이터를 createArticle() 메서드의 매개변수로 받아온다. DTO로 만든 클래스 이름이 ArticleForm이므로 ArticleForm 타입의 form 객체를 매개변수로 선언한다.
    public String createArticle(ArticleForm form){
        // 폼에서 전송한 데이터가 DTO에 잘 담겼는지 확인하기 위한 출력문 (4장에서 로깅하는 방식으로 바꿀 예정)
        System.out.println(form.toString());
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();  // form 객체를 엔티티 객체로 변환 (form 객체 = DTO)
        System.out.println(article.toString()); // DTO가 엔티티로 잘 변환되는지 확인 출력
        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);    // article 엔티티를 저장해 saved 객체에 반환
        System.out.println(saved.toString());   // article이 DB에 잘 저장된느지 확인 출력
        return "";
    }
}
