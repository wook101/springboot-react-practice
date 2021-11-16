package com.wook.book.contorller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wook.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.transaction.Transactional;

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

    @Test
    public void 컨트롤러_저장_테스트() throws Exception{
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
}
