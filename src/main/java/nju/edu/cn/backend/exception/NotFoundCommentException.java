package nju.edu.cn.backend.exception;

/**
 * <p>
 * <p>
 *
 * @author Shenmiu
 */
public class NotFoundCommentException extends GlobalException {
    public NotFoundCommentException(String message, Object... formatArgs) {
        super(message, ExceptionCode.COMMENT_NOT_FOUND, formatArgs);
    }
}
