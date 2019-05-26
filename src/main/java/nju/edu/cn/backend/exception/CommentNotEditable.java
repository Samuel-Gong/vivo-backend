package nju.edu.cn.backend.exception;

/**
 * 不可评论
 * <p>
 *
 * @author Shenmiu
 */
public class CommentNotEditable extends GlobalException {
    public CommentNotEditable(String message, Object... formatArgs) {
        super(message, ExceptionCode.COMMENT_NOT_EDITABLE, formatArgs);
    }
}
