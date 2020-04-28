package org.itachi.codestar.util;

import lombok.extern.slf4j.Slf4j;
import org.itachi.codestar.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;
import java.util.Map;


/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 17:07
 * @author itachi
 */
@Slf4j
public final class ThrowableUtil {
    private ThrowableUtil() {}

    public static ThrowableUtil getInstance() {
        return new ThrowableUtil();
    }

    private String message = "errorMessage";
    private String code = "errorCode";

    public ThrowableUtil setMessage(String message) {
        this.message = message;
        return this;
    }

    public ThrowableUtil setCode(String code) {
        this.code = code;
        return this;
    }

    public HttpStatus handleThrowable(Map<String, Object> result, Throwable throwable) {
        if (log.isDebugEnabled()) {
            log.debug("==========handleThrowable==========");
        }
        return handleCommonThrowable(result, throwable);
    }

    public HttpStatus handleException(Map<String, Object> result, Throwable cause, String message) {
        try {
            if (cause instanceof BaseException) {
                return handleBaseException(result, (BaseException) cause);
            }
            if (cause instanceof BadSqlGrammarException) {
                return handleBadSqlGrammarException(result, (BadSqlGrammarException) cause);
            }
            if (cause instanceof SQLException) {
                return handleSQLException(result, (SQLException) cause);
            }
            Throwable causeParent = cause;
            Throwable causeChild = causeParent.getCause();
            while (causeChild != null && !causeChild.equals(causeParent) && !causeChild.getClass().isAssignableFrom(causeParent.getClass())) {
                if (causeChild instanceof SQLException) {
                    return handleSQLException(result, (SQLException) causeChild);
                }
                causeParent = causeChild;
                causeChild = causeParent.getCause();
            }
            return handleCommonThrowable(result, cause, message);
        } catch (Exception e) {
            return handleCommonThrowable(result, cause, message);
        }
    }

    public HttpStatus handleException(Map<String, Object> result, Throwable cause) {
        return handleException(result, cause, "web server handle data exception!");
    }

    private HttpStatus handleBadSqlGrammarException(Map<String, Object> result, BadSqlGrammarException cause) {
        log.error("====BadSqlGrammarException: ", cause);
        result.put(code, (cause.getSQLException() == null || cause.getSQLException().getErrorCode() == 200 || cause.getSQLException().getErrorCode() == 0) ? 417 : cause.getSQLException().getErrorCode());
        result.put(message, "数据库语句异常，请联系管理员！");
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    private HttpStatus handleSQLException(Map<String, Object> result, SQLException cause) {
        log.error("====SQLException: ", cause);
        result.put(code, (cause.getErrorCode() == 200 || cause.getErrorCode() == 0) ? 417 : cause.getErrorCode());
        result.put(message, "数据库异常，请联系管理员！");
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    private HttpStatus handleBaseException(Map<String, Object> result, BaseException cause) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        if (cause.getStatus() != null) {
            try {
                status = HttpStatus.valueOf(cause.getStatus());
            } catch (Exception e) {
                status = HttpStatus.SERVICE_UNAVAILABLE;
            }
        }
        result.put(code, cause.getCode());
        result.put(message, cause.getMessage());
        return status;
    }

    private HttpStatus handleCommonThrowable(Map<String, Object> result, Throwable cause) {
        return handleCommonThrowable(result, cause, "web server handle data exception!");
    }

    private HttpStatus handleCommonThrowable(Map<String, Object> result, Throwable cause, String message) {
        if (cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) {
            result.put(this.message, cause.getMessage());
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
        String errorMessage = getMessage(cause);
        if (errorMessage != null && !errorMessage.isEmpty()) {
            result.put(this.message, errorMessage);
        } else if (message != null) {
            result.put(this.message, message);
        }
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    public String getMessage(Throwable cause) {
        if (cause == null) {
            return null;
        }
        String errorMessage = null;
        Throwable causeParent = cause;
        Throwable causeChild = causeParent.getCause();
        while (causeChild != null && !causeChild.equals(causeParent) && !causeChild.getClass().isAssignableFrom(causeParent.getClass())) {
            if (causeChild.getMessage() != null && !causeChild.getMessage().isEmpty()) {
                errorMessage = causeChild.getMessage();
                break;
            }
            causeParent = causeChild;
            causeChild = causeParent.getCause();
        }
        return errorMessage;
    }
}
