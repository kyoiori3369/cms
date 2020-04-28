package org.itachi.codestar.exception;

import org.itachi.codestar.error.ServiceError;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 10:51
 * @author itachi
 */
public class ServiceException extends BaseException {
    public ServiceException(Integer code, String message) {
        super(412, code, message);
    }

    public ServiceException(ServiceError.Error error) {
        this(error.getCode(), error.getReasonPhrase());
    }
}
