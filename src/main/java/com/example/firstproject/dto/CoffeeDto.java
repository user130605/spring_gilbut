package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CoffeeDto {
    private Long id;
    private String name; //제목을 받을 필드
    private String price; //내용을 받을 필드

    public Coffee toEntity() {
        return new Coffee(id, name, price);
    }
}
