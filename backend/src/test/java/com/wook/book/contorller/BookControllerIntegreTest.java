package com.wook.book.contorller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wook.book.domain.Book;
import com.wook.book.repositroy.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//통합 테스트 DB에 실제 저장됨
@Transactional //테스트가 끝난 후 rollback된다.
@AutoConfigureMockMvc  //MockMvc를 사용을 위한 설정
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //실제 톰캣이아닌 다른 톰캣으로 테스트
public class BookControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;


    //각각 테스트 코드를 실행하기 하기전에 autoincrement를 1로 초기화
    @BeforeEach
    public void init(){
        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    @DisplayName("컨트롤러 저장 통합 테스트")
    public void save_테스트() throws Exception{
        //given(테스트 하기위한 준비단계)
        Book book = new Book(null,"springboot와react","김유빈");
        String jsonData = new ObjectMapper().writeValueAsString(book); //json으로 요청해야 하기 때문에 json으로 만들기


        //when(테스트 실행)
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonData)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then(검증)
        resultActions
                .andExpect(status().isCreated())   //상태코드 201을 기대
                .andExpect(jsonPath("$.title").value("springboot와react"))
                .andExpect(jsonPath("$.auther").value("김유빈"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("컨트롤러 전체목록 조회 통합 테스트") //h2 db에 데이터 넣고 통합 테스트
    public void findAll() throws Exception{
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null,"파이썬","이동욱"));
        books.add(new Book(null,"자바","박동욱"));
        books.add(new Book(null,"C언어","김동욱"));
        bookRepository.saveAll(books);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));//accept는 Response, APPLICATION_JSON_UTF8 한글깨짐 방지

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].title").value("파이썬"))
                .andExpect(jsonPath("$.[1].title").value("자바"))
                .andExpect(jsonPath("$.[2].title").value("C언어"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("id를 통해 한건 가져오기 통합 테스트")
    public void findById() throws Exception{
        //given
        Long id = 1L;
        Book book = new Book(null,"뇌를 자극하는 c++","이두희");
        bookRepository.save(book);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                                            .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                .andExpect(jsonPath("$.title").value("뇌를 자극하는 c++"))
                .andExpect(jsonPath("$.auther").value("이두희"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("1건 수정하기 통합 테스트")
    public void updateByid() throws Exception{ //boo1을 book2로 수정한다.
        //given
        Long id = 1L;
        Book book1 = new Book(id,"뇌를 자극하는 c++","이두희");
        bookRepository.save(book1);
        Book book2 = new Book(null,"뇌를 자극하는 android","김유지");
        String jsonData = new ObjectMapper().writeValueAsString(book2);

        //when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonData)
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                .andExpect(jsonPath("$.title").value("뇌를 자극하는 android"))
                .andExpect(jsonPath("$.auther").value("김유지"))
                .andDo(MockMvcResultHandlers.print());

    }




}
