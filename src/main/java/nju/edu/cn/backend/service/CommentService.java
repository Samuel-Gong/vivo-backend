package nju.edu.cn.backend.service;

import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.dao.CommentRepository;
import nju.edu.cn.backend.dao.LectureRepository;
import nju.edu.cn.backend.entity.Comment;
import nju.edu.cn.backend.entity.Lecture;
import nju.edu.cn.backend.exception.CommentNotEditable;
import nju.edu.cn.backend.exception.NotFoundLectureException;
import nju.edu.cn.backend.vo.CommentVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
     * 创建评论
     *
     * @param lectureId 讲座 id
     * @param commentVO 评论内容
     * @return 创建的评论
     */
    public CommentVO createComment(Long lectureId, CommentVO commentVO) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> {
            log.error("找不到 id 为 [{}] 的讲座", lectureId);
            return new NotFoundLectureException("找不到讲座");
        });
        LocalDateTime now = LocalDateTime.now();
        if (isAddable(now, lecture)) {
            Comment comment = commentRepository.save(Comment.builder()
                    .lectureId(lectureId)
                    .nickName(commentVO.getNickName())
                    .text(commentVO.getText())
                    .build());
            return modelMapper.map(comment, CommentVO.class);
        } else {
            log.error("讲座评论不可添加，讲座 id 为 [{}]，开始时间为 [{}]，有效期为 [{}]，发送时间为 [{}]", lectureId, lecture.getStart(), lecture.getValidityDays(), now);
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
}
