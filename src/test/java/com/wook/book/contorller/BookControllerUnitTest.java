package com.wook.book.contorller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wook.book.domain.Book;
import com.wook.book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void 컨트롤러_저장_테스트() throws Exception{
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
}
