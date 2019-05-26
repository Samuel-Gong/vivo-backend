package nju.edu.cn.backend.controller;

import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.service.LectureService;
import nju.edu.cn.backend.vo.LectureCommentVO;
import nju.edu.cn.backend.vo.LectureVO;
import org.springframework.web.bind.annotation.*;

/**
 * 讲座 controller
 * <p>
 *
 * @author Shenmiu
 */
@Slf4j
@RestController
@RequestMapping(value = "/lectures")
public class LectureController {

    /**
     * 讲座服务
     */
    private LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    /**
     * 创建讲座基本信息
     *
     * @param lectureVO 讲座基本信息
     * @return 讲座基本
     */
    @PostMapping
    public LectureVO createLecture(@RequestBody LectureVO lectureVO) {
        return lectureService.createLecture(lectureVO);
    }

    /**
     * 根据讲座 id 获取讲座信息
     *
     * @param id 讲座 id
     * @return 讲座信息
     */
    @GetMapping("/{id}")
    public LectureCommentVO queryLecture(@PathVariable("id") Long id) {
        return lectureService.queryLecture(id);
    }

}
