package nju.edu.cn.backend.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论展示
 * <p>
 *
 * @author Shenmiu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    /**
     * 评论 id
     */
    private Long id;

    /**
     * 评论使用的匿名名称
     */
    private String nickName;

    /**
     * 评论内容
     */
    private String text;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdAt;

}
