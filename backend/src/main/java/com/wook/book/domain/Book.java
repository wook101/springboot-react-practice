package com.wook.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //서버 실행시 테이블이 h2 DB에 생성된다.
public class Book {
    @Id//pk를 해당 변수로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//해당 데이터베이스 번호 증가 전략 따라감
    private Long id;
    private String title;
    private String auther;

}
