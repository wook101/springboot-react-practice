package com.wook.book.contorller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wook.book.domain.Book;
import com.wook.book.repositroy.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @DisplayName("컨트롤러 저장 통합 테스트")
    public void sava_테스트() throws Exception{
        //given(테스트 하기위한 준비단계)
        Book book = new Book(null,"Springboot와 react","김유빈");
        String jsonData = new ObjectMapper().writeValueAsString(book); //json으로 요청해야 하기 때문에 json으로 만들기


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
    @DisplayName("컨트롤러 전체목록 조회 통합 테스트") //h2 db에 데이터 넣고 통합 테스트
    public void findAll() throws Exception{
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book(4L,"파이썬","이동욱"));
        books.add(new Book(5L,"자바","박동욱"));
        books.add(new Book(6L,"C언어","김동욱"));
        bookRepository.saveAll(books);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));//accept는 Response, APPLICATION_JSON_UTF8 한글깨짐 방지

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[3].title").value("파이썬"))
                .andExpect(jsonPath("$.[4].title").value("자바"))
                .andExpect(jsonPath("$.[5].title").value("C언어"))
                .andDo(MockMvcResultHandlers.print());

    }


}
