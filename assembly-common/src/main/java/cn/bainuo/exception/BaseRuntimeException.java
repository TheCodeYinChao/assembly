package cn.bainuo.exception;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * User: zyc
 * Date: 2018-05-09
 * Time: 18:53
 * Version ：1.0
 * Description:
 */
@Data
public class BaseRuntimeException extends RuntimeException {
    private Throwable cause = null;
    private String errorCode;
    private String errorMsg;


    public BaseRuntimeException() {
    }

    public BaseRuntimeException( String message) {
        super(message);
    }
    public BaseRuntimeException( String errorCode ,String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public BaseRuntimeException(String errorCode,String message, Throwable cause) {
        super(message);
        this.cause = cause;
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    public String getExceptionMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        }
        if (this.cause != null) {
            return this.cause.toString();
        }
        return null;
    }
}
