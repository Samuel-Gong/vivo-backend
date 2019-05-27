package nju.edu.cn.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nju.edu.cn.backend.vo.CommentVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private CommentVO commentVO;

    @Before
    public void beforeEach() {
        commentVO = CommentVO.builder()
                .nickName("fjj")
                .text("nju hackathon")
                .build();
    }

    @Test
    public void shouldCreateComment() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                .param("lectureId", "1")
                .content(mapper.writeValueAsString(commentVO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals("{\"nickName\":\"fjj\",\"text\":\"nju hackathon\"}",
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldNotFoundLecture() throws Exception {
        mockMvc.perform(post("/comments")
                .param("lectureId", "3")
                .content(mapper.writeValueAsString(commentVO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldNotEditCommentAfter() throws Exception {
        mockMvc.perform(post("/comments")
                .param("lectureId", "2")
                .content(mapper.writeValueAsString(commentVO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void shouldNotAddCommentBefore() throws Exception {
        mockMvc.perform(post("/comments")
                .param("lectureId", "4")
                .content(mapper.writeValueAsString(commentVO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void shouldLike() throws Exception {
        MvcResult result = mockMvc.perform(patch("/comments/{id}", 1)
                .param("like", "true"))
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals("{\"id\":1,\"nickName\":\"fjj\",\"text\":\"good\",\"likes\":101,\"createdAt\":\"2019-05-26 19:56:00.100\"}"
                , result.getResponse().getContentAsString(), false);
    }

    @Test
    public void shouldUnlike() throws Exception {
        MvcResult result = mockMvc.perform(patch("/comments/{id}", 1)
                .param("like", "false"))
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals("{\"id\":1,\"nickName\":\"fjj\",\"text\":\"good\",\"likes\":99,\"createdAt\":\"2019-05-26 19:56:00.100\"}"
                , result.getResponse().getContentAsString(), false);
    }
}