package nju.edu.cn.backend.service;

import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.dao.CommentRepository;
import nju.edu.cn.backend.dao.LectureRepository;
import nju.edu.cn.backend.entity.Lecture;
import nju.edu.cn.backend.entity.QComment;
import nju.edu.cn.backend.exception.NotFoundLectureException;
import nju.edu.cn.backend.vo.CommentVO;
import nju.edu.cn.backend.vo.LectureCommentVO;
import nju.edu.cn.backend.vo.LectureVO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 讲座服务
 * <p>
 *
 * @author Shenmiu
 */
@Slf4j
@Service
public class LectureService {

    /**
     * 讲座数据访问
     */
    private LectureRepository lectureRepository;

    /**
     * 评论数据访问
     */
    private CommentRepository commentRepository;

    /**
     * 实体-VO 转换器
     */
    private ModelMapper modelMapper;

    public LectureService(LectureRepository lectureRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.lectureRepository = lectureRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * 创建讲座
     *
     * @param lectureVO 讲座信息
     * @return 持久化的讲座
     */
    @Transactional(rollbackFor = Exception.class)
    public LectureVO createLecture(LectureVO lectureVO) {
        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureVO.getTitle())
                .speaker(lectureVO.getSpeaker())
                .start(lectureVO.getStart())
                .validityDays(lectureVO.getValidityDays())
                .build());

        return modelMapper.map(lecture, LectureVO.class);
    }

    /**
     * 根据讲座id获取讲座数据
     *
     * @param id 讲座id
     * @return 讲座及评论数据
     */
    public LectureCommentVO queryLecture(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> {
            log.error("找不到 id 为 [{}] 的讲座", id);
            return new NotFoundLectureException("找不到讲座");
        });

        // 对应讲座所有评论
        QComment qComment = QComment.comment;
        BooleanBuilder builder = new BooleanBuilder(qComment.lectureId.eq(id));
        List<CommentVO> comments = new ArrayList<>();
        commentRepository.findAll(builder, new Sort(Sort.Direction.ASC, "createdAt"))
                .forEach(comment -> comments.add(modelMapper.map(comment, CommentVO.class)));

        // 是否可编辑
        boolean editable = isEditable(lecture.getStart(), lecture.getValidityDays());

        LectureVO lectureVO = modelMapper.map(lecture, LectureVO.class);

        LectureCommentVO lectureCommentVO = LectureCommentVO.builder()
                .lecture(lectureVO)
                .editable(editable)
                .comments(comments)
                .build();

        // 最后一条评论时间，若无评论，设置为讲座开始时间
        if (comments.isEmpty()) {
            lectureCommentVO.setLastCommentTime(lecture.getStart());
        } else {
            lectureCommentVO.setLastCommentTime(comments.get(comments.size() - 1).getCreatedAt());
        }

        return lectureCommentVO;
    }

    /**
     * 当前讲座评论是否可编辑
     *
     * @param start        讲座开始时间
     * @param validityDays 有效天数
     * @return 是否可编辑
     */
    public boolean isEditable(LocalDateTime start, int validityDays) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(start) && now.isBefore(start.plusDays(validityDays));
    }
}
