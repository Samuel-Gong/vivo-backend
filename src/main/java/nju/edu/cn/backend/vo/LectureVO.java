package nju.edu.cn.backend.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 讲座信息展示
 * <p>
 *
 * @author Shenmiu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureVO {

    /**
     * 讲座id
     */
    private Long id;

    /**
     * 讲座标题
     */
    private String title;

    /**
     * 主讲人
     */
    private String speaker;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    /**
     * 有效天数
     */
    private Integer validityDays;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expire;

}
