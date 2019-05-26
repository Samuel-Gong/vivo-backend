package nju.edu.cn.backend.exception;

import nju.edu.cn.backend.controller.LectureController;
import nju.edu.cn.backend.service.LectureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {LectureController.class})
@AutoConfigureJsonTesters
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LectureService lectureService;

    @Test
    public void shouldReturnLectureNotFound() throws Exception {
        given(lectureService.queryLecture(1L))
                .willThrow(new NotFoundLectureException(""));

        mvc.perform(get("/lectures/{id}", 1))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldReturnUnknownException() throws Exception {
        given(lectureService.queryLecture(1L))
                .willThrow(new RuntimeException());

        mvc.perform(get("/lectures/{id}", 1))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }
}