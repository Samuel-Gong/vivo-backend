package nju.edu.cn.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 讲座信息
 * <p>
 *
 * @author Shenmiu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lecture")
public class Lecture {

    /**
     * 讲座 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 讲座标题
     */
    private String title;

    /**
     * 讲座开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    /**
     * 有效天数
     */
    @Column(name = "v_days")
    private Integer validityDays;

    /**
     * 主讲人
     */
    private String speaker;

}
