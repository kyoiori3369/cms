package org.itachi.codestar.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 10:24
 * @author itachi
 */
@ControllerAdvice
public class GlobalExceptionHandler extends AbstractExceptionHandler {
    public GlobalExceptionHandler() {
        super();
        CODE = "code";
        MESSAGE = "message";
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity handleException(Throwable throwable, HttpServletRequest request) {
        StringBuilder body = new StringBuilder();
        HttpStatus status = handleThrowable(request, throwable, body);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON_UTF8).body(body.toString());
    }
}
