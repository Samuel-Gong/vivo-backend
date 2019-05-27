package nju.edu.cn.backend.controller;

import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.service.CommentService;
import nju.edu.cn.backend.vo.CommentVO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
     * 刷新评论
     *
     * @param lectureId       讲座 id
     * @param lastCommentTime 最后一条评论的时间
     * @return 评论列表
     */
    @GetMapping
    public List<CommentVO> refreshComments(@RequestParam("lectureId") Long lectureId,
                                           @RequestParam("lastCommentTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime lastCommentTime) {
        return commentService.refreshComments(lectureId, lastCommentTime);
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

    /**
     * 点赞
     */
    @PatchMapping("/{id}")
    public CommentVO likeComment(@PathVariable("id") Long id, @RequestParam("like") Boolean like) {
        return commentService.like(id, like);
    }
}
