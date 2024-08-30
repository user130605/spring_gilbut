package com.example.firstproject.controller;

import com.example.firstproject.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j //로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired  // 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입
    private ArticleRepository articleRepository;

    // 뷰 페이지를 보여주는 메서드
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    // 뷰 페이지에서 전송한 폼 데이터를 받는 메서드
    // 뷰 페이지에서 폼 데이터를 post 방식으로 전송했으므로 컨트롤러에서 받을 때도 @PostMapping()으로 받는다.
    // @PostMapping("/articles/create") : new.mustache에서 <form> 태그에 action="/articles/create"로 설정했으므로
    @PostMapping("/articles/create")
    // 폼에서 전송한 데이터를 createArticle() 메서드의 매개변수로 받아온다. DTO로 만든 클래스 이름이 ArticleForm이므로 ArticleForm 타입의 form 객체를 매개변수로 선언한다.
    public String createArticle(ArticleForm form){
        // 폼에서 전송한 데이터가 DTO에 잘 담겼는지 확인하기 위한 출력문 (4장에서 로깅하는 방식으로 바꿀 예정)
        log.info(form.toString());
        //System.out.println(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();  // form 객체를 엔티티 객체로 변환 (form 객체 = DTO)
        log.info(article.toString());
        //System.out.println(article.toString()); // DTO가 엔티티로 잘 변환되는지 확인 출력

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);    // article 엔티티를 저장해 saved 객체에 반환
        log.info(saved.toString());
        //System.out.println(saved.toString());   // article이 DB에 잘 저장됐는지 확인 출력
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){  // 매개변수로 id 받아 오기
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰  페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // DB에서 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity); // articleEntity를 article로 등록
        // 뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){     // 매개변수로 DTO 받아 오기
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();    // DTO를 엔티티로 변환하기
        log.info(articleEntity.toString());
        // 2. 엔티티를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터 값을 갱신하기
        if (target != null){
            articleRepository.save(articleEntity);  // 엔티티를 DB에 저장(갱신)
        }
        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        if (target != null){
            articleRepository.delete(target);   // delete() 메서드로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
