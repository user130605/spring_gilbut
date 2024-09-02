package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CoffeeApiController {
    @Autowired  // 게시글 리파지터리 주입
    private CoffeeRepository coffeeRepository;

    // GET
    @GetMapping("/api/coffees")
    public Iterable<Coffee> index(){
        return coffeeRepository.findAll();
    }
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id){
        return coffeeRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        return coffeeRepository.save(coffee);
    }

    // PATCH
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id,
                                          @RequestBody CoffeeDto dto){
        // 1. DTO -> 엔티티 변환하기
        Coffee coffee = dto.toEntity();   // dto를 엔티티로 변환
        log.info("id: {}, coffee: {}", id, coffee.toString());

        // 2. DB에 대상 엔티티가 있는지 타깃 조회하기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        // 3. 대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 잘못된 요청 처리하기
        if (target == null || id != coffee.getId()){
            log.info("잘못된 요청! id: {}, coffee: {}", id, coffee.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);    // ResponseEntity 반환
        }

        // 4. 업데이트 및 정상 응답(200)하기
        target.patch(coffee);  // 기존 데이터에서 새 데이터 붙이기
        Coffee updated = coffeeRepository.save(target);  // coffee 엔티티 DB에 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);  // 정상 응답
    }

    // DELETE
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        // 1. 대상 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 3. 대상 삭제하기
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
        // ResponseEntity의 build() 메서드는 HTTP 응답의 body가 없는 ResponseEntity 객체를 생성한다.
    }
}
