package nju.edu.cn.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 评论
 * <p>
 *
 * @author Shenmiu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    /**
     * 评论 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 讲座 id
     */
    @Column(name = "lecture_id")
    private Long lectureId;
    /**
     * 评论使用的匿名名称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 评论内容
     */
    @Type(type = "text")
    private String text;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "created_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

}
