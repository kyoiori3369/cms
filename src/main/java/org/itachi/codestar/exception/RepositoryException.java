package org.itachi.codestar.exception;

import org.itachi.codestar.error.ServiceError;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 10:50
 * @author itachi
 */
public class RepositoryException extends BaseException {
    public RepositoryException(Integer code, String message) {
        super(417, code, message);
    }

    public RepositoryException(ServiceError.Error error) {
        this(error.getCode(), error.getReasonPhrase());
    }
}
