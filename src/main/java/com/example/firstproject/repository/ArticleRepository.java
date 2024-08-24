package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

// CrudRepository : JPA에서 제공하는 인터페이스로 이를 상속해 엔티티를 관리(생성, 조회, 수정, 삭제)할 수 있다.
// DB에 데이터를 생성하고, 읽고, 수정하고, 삭제하는 기본 동작을 추가 코드로 구현할 필요 없이 CrudRepository에서 상속받아 사용할 수 있다.
// <Article, Long> : Article -> 관리 대상 엔티티의 클래스 타입 / Long : 관리 대상 엔티티의 대푯값 타입
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
}
