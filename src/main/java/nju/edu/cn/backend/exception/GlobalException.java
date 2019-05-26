package nju.edu.cn.backend.exception;

/**
 * 全局异常
 * <p>
 *
 * @author Shenmiu
 */
class GlobalException extends RuntimeException {
    private final transient ExceptionCode code;
    private final transient Object[] formatArgs;

    GlobalException(String message, ExceptionCode code, Object... formatArgs) {
        super(message);
        this.code = code;
        this.formatArgs = formatArgs;
    }

    ExceptionCode getCode() {
        return this.code;
    }

    Object[] getFormatArgs() {
        return this.formatArgs;
    }
}