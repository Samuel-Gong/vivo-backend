package nju.edu.cn.backend.exception;

/**
 * 错误码
 *
 * @author Shenmiu
 */
public enum ExceptionCode {

    /**
     * 没有相应讲座
     */
    LECTURE_NOT_FOUND("lecture_not_found", 404),
    /**
     * 没有相应评论
     */
    COMMENT_NOT_FOUND("comment_not_found", 404),
    /**
     * 不再评论时间内，评论不可编辑
     */
    COMMENT_NOT_EDITABLE("comment_not_editable", 416),
    /**
     * 昵称或评论内容中含有敏感词汇
     */
    TEXT_SENSITIVE("text_sensitive", 451),
    /**
     * 文本分析调用失败
     */
    TEXT_INVOKE_FAILED("text_invoke_failed", 500);

    /**
     * i18n 映射键
     */
    private String key;

    /**
     * http状态码
     */
    private int httpCode;

    ExceptionCode(String key, int httpCode) {
        this.key = key;
        this.httpCode = httpCode;
    }

    public int httpCode() {
        return httpCode;
    }

    public String uniqueKey() {
        return key.toLowerCase();
    }
}
