package nju.edu.cn.backend.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 讲座及评论信息展示
 * <p>
 *
 * @author Shenmiu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureCommentVO {

    /**
     * 讲座信息
     */
    private LectureVO lecture;

    /**
     * 是否可以编辑
     */
    private Boolean editable;

    /**
     * 讲座对应评论列表
     */
    private List<CommentVO> comments;

    /**
     * 最后一条评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lastCommentTime;

}
