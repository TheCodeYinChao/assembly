package cn.bainuo.exception;

/**
 * User: zyc
 * Date: 2018-05-09
 * Time: 18:50
 * Version ：1.0
 * Description:
 */
public class ServiceException extends BaseRuntimeException {
    public ServiceException() {
    }

    public ServiceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
