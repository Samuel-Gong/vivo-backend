package nju.edu.cn.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 异常响应 vo
 * <p>
 *
 * @author Shenmiu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseVO {
    /**
     * 异常消息
     */
    private String message;
    /**
     * 当前时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    /**
     * 异常
     */
    private String type;
}