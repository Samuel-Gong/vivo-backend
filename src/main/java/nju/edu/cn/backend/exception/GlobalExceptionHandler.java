package nju.edu.cn.backend.exception;

import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.vo.ExceptionResponseVO;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * 全局异常处理
 * <p>
 *
 * @author Shenmiu
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<ExceptionResponseVO> errorMessage(GlobalException ex) {
        log.error("GlobalException error", ex);
        Locale locale = Locale.CHINA;
        String code = ex.getCode().uniqueKey().toLowerCase();
        String message = ex.getMessage();

        try {
            message = this.messageSource.getMessage(code, ex.getFormatArgs(), locale);
        } catch (Exception var6) {
            log.error("catch Throwable but e", var6);
        }

        return ResponseEntity.status(ex.getCode().httpCode()).body(new ExceptionResponseVO(message, LocalDateTime.now(), ex.getCode().uniqueKey()));
    }
}
