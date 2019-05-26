package nju.edu.cn.backend.exception;

import lombok.extern.slf4j.Slf4j;
import nju.edu.cn.backend.vo.ExceptionResponseVO;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
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
        log.warn("GlobalException error", ex);
        Locale locale = Locale.CHINA;
        String code = ex.getCode().uniqueKey();
        String message = this.messageSource.getMessage(code, ex.getFormatArgs(), locale);
        return ResponseEntity.status(ex.getCode().httpCode()).body(new ExceptionResponseVO(message, LocalDateTime.now(), code));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ExceptionResponseVO> errorMessage(Throwable ex) {
        log.error("Unknown error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponseVO("Unknown error", LocalDateTime.now(), "Unknown"));
    }
}
