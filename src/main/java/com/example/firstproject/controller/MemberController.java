package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.firstproject.repository.MemberRepository;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members/new")
    public String newMemberForm(){
        return "members/new";
    }

    // 회원 가입 페이지
    @GetMapping("/signup")
    public String signUpPage(){
        return "members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberForm){
        log.info(memberForm.toString());

        // 1. DTO를 엔티티로 변환
        Member member = memberForm.toEntity();
        log.info(member.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        return "redirect:/members/"+ saved.getId();
    }

    // 상세 페이지
    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "members/show";
    }

    // 회원 목록 페이지
    @GetMapping("/members")
    public String index(Model model){
        Iterable<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members/index";
    }
}
