package com.wook.book.repositroy;

import com.wook.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repositroy를 적어야 스프링 ioc에 빈으로 등록되지만
//JpaRepositroy를 extends하면 생략이 가능하다.
//JpaRepositroy는 CRUD함수를 제공한다.
public interface BookRepository extends JpaRepository<Book, Long> {
}
