package org.itachi.codestar.exception;

/**
 * Created by itachi on 2017/3/18.
 * @author itachi
 */
public class BaseException extends Exception {

    private static final int FAILURE = 0;
    private static final Integer ERROR = 500;

    private final Integer code;
    private final Integer status;

    public Integer getCode() {
        return code;
    }

    public Integer getStatus() {
        return status;
    }

    public BaseException(Integer status, Integer code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
        this.status = FAILURE;
        this.code = ERROR;
    }

    public BaseException() {
        super();
        this.status = FAILURE;
        this.code = ERROR;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.status = FAILURE;
        this.code = ERROR;
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.status = FAILURE;
        this.code = ERROR;
    }

    public BaseException(Integer status, Integer code, Throwable cause) {
        super(cause);
        this.status = status;
        this.code = code;
    }
}
