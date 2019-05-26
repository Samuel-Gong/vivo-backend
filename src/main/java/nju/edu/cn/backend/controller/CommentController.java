package nju.edu.cn.backend.controller;

import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.service.CommentService;
import nju.edu.cn.backend.vo.CommentVO;
import org.springframework.web.bind.annotation.*;

/**
 * 评论控制器
 * <p>
 *
 * @author Shenmiu
 */
@Slf4j
@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    /**
     * 评论服务
     */
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 创建评论
     *
     * @param lectureId 讲座 id
     * @param commentVO 评论展示信息
     * @return 新创建的评论
     */
    @PostMapping
    public CommentVO createComment(@RequestParam("lectureId") Long lectureId, @RequestBody CommentVO commentVO) {
        return commentService.createComment(lectureId, commentVO);
    }
}
