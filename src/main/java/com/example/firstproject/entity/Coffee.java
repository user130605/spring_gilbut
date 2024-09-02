package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor  // 기본 생성자 추가 어노테이션
@ToString
@Entity
@Getter
public class Coffee {
    @Id // 엔티티의 대푯값 지정
    // strategy = GenerationType.IDENTITY : 데이터를 생성할 때마다 DB가 알아서 id에 1,2,3,4 등 값을 넣어 준다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 기능 추가(숫자가 자동으로 매겨짐/대푯값을 자동으로 생성하게 한다.)
    private Long id;
    @Column // title 필드 선언, DB 테이블의 title 열과 연결됨
    private String name;
    @Column // content 필드 선언, DB 테이블의 content 열과 연결됨
    private String price;

    public void patch(Coffee coffee) {
        if (coffee.name != null)
            this.name = coffee.name;
        if (coffee.price != null)
            this.price = coffee.price;
    }
}
