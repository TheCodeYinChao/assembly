package cn.bainuo.exception.exceptionenum;

/**
 * 错误码接口
 *
 * @author DHC
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * @return code
     */
    String getCode();

    /**
     * 获取错误码信息
     * @return message
     */
    String getMessage();
}
