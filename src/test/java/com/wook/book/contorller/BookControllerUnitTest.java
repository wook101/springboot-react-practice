package com.wook.book.contorller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wook.book.domain.Book;
import com.wook.book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//단위 테스트(@WebMvcTest가 Controller, Filter, ControllerAdvice등을 메모리에 띄운다)
//오로지 컨트롤러만 테스트 하기위함
@Slf4j //로그
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    //가짜 bookService객체를 ioc에 등록
    @MockBean
    private BookService bookService;


    @Test
    @DisplayName("컨트롤러_저장_테스트")
    public void save() throws Exception{
        //given(테스트 하기위한 준비단계)
        Book book = new Book(null,"Springboot와 react","김유빈");
        String jsonData = new ObjectMapper().writeValueAsString(book); //json으로 요청해야 하기 때문에 json으로 만들기
        when(bookService.save(book)).thenReturn(new Book(7L,"Springboot와 react","김유빈")); //가정해서 return되는 값을 미리 지정

        //when(테스트 실행)
        ResultActions resultActions = mockMvc.perform(post("/book")
                                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                                            .content(jsonData)
                                            .accept(MediaType.APPLICATION_JSON_UTF8));

        //then(검증)
        resultActions
                .andExpect(status().isCreated())   //상태코드 201을 기대
                .andExpect(jsonPath("$.auther").value("김유빈"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("컨트롤러 전체목록 조회 단위 테스트")
    public void findAll() throws Exception{
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L,"파이썬","이동욱"));
        books.add(new Book(2L,"자바","박동욱"));
        books.add(new Book(3L,"C언어","김동욱"));
        when(bookService.selectAll()).thenReturn(books);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));//accept는 Response, APPLICATION_JSON_UTF8 한글깨짐 방지

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("파이썬"))
                .andExpect(jsonPath("$.[1].title").value("자바"))
                .andExpect(jsonPath("$.[2].title").value("C언어"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("id로 한건 가젹오기")
    public void findById() throws Exception{
        //given
        Long id = 10L;
        Book book = new Book(id,"코딩의정석","이수지");
        when(bookService.selectById(id)).thenReturn(book);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("코딩의정석"))
                .andExpect(jsonPath("$.auther").value("이수지"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("컨트롤러 한 건 수정하기 단위테스트")
    public void updateById() throws Exception{
        //given
        Long id = 1L;
        Book book = new Book(null,"뇌를 자극하는 c++","이두희");
        String jsonData = new ObjectMapper().writeValueAsString(book);
        when(bookService.update(id,book)).thenReturn(new Book(null,"뇌를 자극하는 c++","이두희"));

        //when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}",id)
                                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                                            .content(jsonData)
                                            .accept(MediaType.APPLICATION_JSON_UTF8));
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("뇌를 자극하는 c++"))
                .andExpect(jsonPath("$.auther").value("이두희"))
                .andDo(MockMvcResultHandlers.print());


    }


}
