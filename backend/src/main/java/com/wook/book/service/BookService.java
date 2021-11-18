package com.wook.book.service;

import com.wook.book.domain.Book;
import com.wook.book.repositroy.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//기능을 정의할 수 있고, 트랜잭션을 관리할 수 있다.
//레파지토리의 여러개에 함수 실행한다. commit 또는 문제가 생기면 rollback함
@Service
public class BookService {

    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    //저장하기
    @Transactional
    public Book save(Book book){
        return bookRepository.save(book);
    }

    //한건 가져오기
    @Transactional(readOnly = true)
    public Book selectById(Long id){
        return bookRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException("id를 다시 확인해주세요"));
    }

    //모두 가져오기
    @Transactional
    public List<Book> selectAll(){
        return bookRepository.findAll();
    }

    //갱신하기
    @Transactional
    public Book update(Long id, Book book){
        //DB에서 데이터 들고와서 영속화 한다. > 영속성 컨텍스트에 보관
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 다시 확인해주세요"));
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuther(book.getAuther());
        return bookEntity;
    }//함수 종료 - 트랜잭션 종료될때 > 영속화 되어있는 데이터를 DB에 갱신한다.(flush) > commit됨


    //삭제하기
    @Transactional
    public String delete(Long id){
        bookRepository.deleteById(id); //오류가 발생하면 내부적으로 익셉션 발생
        return "Ok";
    }


}
