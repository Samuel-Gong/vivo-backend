package nju.edu.cn.backend.exception;

/**
 * 无法找到讲座
 * <p>
 *
 * @author Shenmiu
 */
public class NotFoundLectureException extends GlobalException {
    public NotFoundLectureException(String message, Object... formatArgs) {
        super(message, ExceptionCode.LECTURE_NOT_FOUND, formatArgs);
    }
}
