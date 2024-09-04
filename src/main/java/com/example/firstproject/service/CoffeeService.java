package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;  // 게시글 리파지터리 객체 주입

    public Iterable<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        if (coffee.getId() != null){
            return null;
        }
        return coffeeRepository.save(coffee);
    }

    public Coffee update(Long id, CoffeeDto coffeeDto) {
        // 1. DTO -> 엔티티 변환하기
        Coffee coffee = coffeeDto.toEntity();   // dto를 엔티티로 변환
        log.info("id: {}, coffee: {}", id, coffee.toString());

        // 2. DB에 대상 엔티티가 있는지 타깃 조회하기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        // 3. 대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 잘못된 요청 처리하기
        if (target == null || id != coffee.getId()){
            log.info("잘못된 요청! id: {}, coffee: {}", id, coffee.toString());
            return null;    // ResponseEntity 반환
        }

        // 4. 업데이트 및 정상 응답(200)하기
        target.patch(coffee);  // 기존 데이터에서 새 데이터 붙이기
        return coffeeRepository.save(target);  // 정상 응답
    }

    public Coffee delete(Long id) {
        // 1. 대상 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null){
            return null;
        }

        // 3. 대상 삭제하기
        coffeeRepository.delete(target);
        return target;
        // ResponseEntity의 build() 메서드는 HTTP 응답의 body가 없는 ResponseEntity 객체를 생성한다.
    }
}
