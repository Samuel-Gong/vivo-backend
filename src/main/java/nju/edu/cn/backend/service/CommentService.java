package nju.edu.cn.backend.service;

import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.dao.CommentRepository;
import nju.edu.cn.backend.dao.LectureRepository;
import nju.edu.cn.backend.entity.Comment;
import nju.edu.cn.backend.entity.Lecture;
import nju.edu.cn.backend.entity.QComment;
import nju.edu.cn.backend.exception.CommentNotEditable;
import nju.edu.cn.backend.exception.NotFoundCommentException;
import nju.edu.cn.backend.exception.NotFoundLectureException;
import nju.edu.cn.backend.vo.CommentVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论服务
 * <p>
 *
 * @author Shenmiu
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService {

    /**
     * 讲座数据访问
     */
    private LectureRepository lectureRepository;

    /**
     * 评论数据访问
     */
    private CommentRepository commentRepository;

    /**
     * 实体转换
     */
    private ModelMapper modelMapper;

    public CommentService(LectureRepository lectureRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.lectureRepository = lectureRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * 刷新评论
     *
     * @param lectureId       讲座 id
     * @param lastCommentTime 最后一条评论时间
     * @return 新评论列表
     */
    public List<CommentVO> refreshComments(Long lectureId, LocalDateTime lastCommentTime) {
        QComment qComment = QComment.comment;
        BooleanBuilder builder = new BooleanBuilder(qComment.lectureId.eq(lectureId));
        builder.and(qComment.createdAt.after(lastCommentTime));

        List<CommentVO> comments = new ArrayList<>();
        commentRepository.findAll(builder)
                .forEach(comment -> comments.add(modelMapper.map(comment, CommentVO.class)));

        return comments;
    }

    /**
     * 创建评论
     *
     * @param lectureId 讲座 id
     * @param commentVO 评论内容
     * @return 创建的评论
     */
    public CommentVO createComment(Long lectureId, CommentVO commentVO) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> {
            log.warn("找不到 id 为 [{}] 的讲座", lectureId);
            return new NotFoundLectureException("找不到讲座");
        });

        LocalDateTime now = LocalDateTime.now();
        if (isAddable(now, lecture)) {
            Comment comment = commentRepository.save(Comment.builder()
                    .lectureId(lectureId)
                    .nickName(commentVO.getNickName())
                    .text(commentVO.getText().trim())
                    // 点赞数初始化为0
                    .likes(0L)
                    .build());
            return modelMapper.map(comment, CommentVO.class);
        } else {
            log.warn("讲座评论不可添加，讲座 id 为 [{}]，开始时间为 [{}]，有效期为 [{}]，发送时间为 [{}]", lectureId, lecture.getStart(), lecture.getValidityDays(), now);
            throw new CommentNotEditable("不可添加评论，讲座未开始或讲座评论有效期已过");
        }
    }

    /**
     * 在讲座开始时间后，有效时间前即可编辑
     *
     * @param lecture 讲座
     * @return 是否可编辑
     */
    private boolean isAddable(LocalDateTime now, Lecture lecture) {
        return now.isAfter(lecture.getStart()) && now.isBefore(lecture.getStart().plusDays(lecture.getValidityDays()));
    }

    /**
     * 点赞
     *
     * @param id   评论 id
     * @param like 是否点赞
     * @return 点赞数量
     */
    public CommentVO like(Long id, Boolean like) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            log.warn("找不到对应评论，评论id [{}]", id);
            return new NotFoundCommentException("找不到对应评论");
        });

        if (like) {
            comment.setLikes(comment.getLikes() + 1);
        } else {
            comment.setLikes(comment.getLikes() - 1);
        }

        return modelMapper.map(commentRepository.save(comment), CommentVO.class);
    }
}
