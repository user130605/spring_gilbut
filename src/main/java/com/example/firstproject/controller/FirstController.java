package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    // niceToMeetYou() 메서드로 greetings.mustache 페이지를 반환하려면 파일 이름인 greetings만 반환값으로 적어 주면 된다.
    // GetMapping("/hi") (url 요청 접수) : 웹 브라우저에서 localhost:8080/hi로 접속하면 greetings.mustache 파일을 찾아 반환하라는 뜻
    // 정리 : 컨트롤러를 만들 때 먼저 컨트롤를 선언(@Comtroller)하고, 반환값으로 보여 줄 페이지의 이름만 따서 적은 다음(return "greetings";), URL 요청을 접수해야(@GetMapping("/hi")) 제대로 동작한다.
    @GetMapping("/hi")
    public String niceToMeetYou(Model model){ // model 객체 받아 오기
        model.addAttribute("username", "hongpark");
        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "홍길동");
        return "goodbye";
    }
}
