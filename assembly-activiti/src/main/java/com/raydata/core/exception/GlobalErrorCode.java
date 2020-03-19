package com.raydata.core.exception;

/**
 * 全局错误码
 */

public enum GlobalErrorCode{
    SUCCESS("0", "OK"),

    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    SERVER_ERROR("500","server_error"),
    BAD_CREDENTIALS("406","bad_credentials"),


    SYSTEM_ERROR("400000","系统错误"),
    SYSTEM_BUSY("400001","系统繁忙"),
    HTTP_MASTER_REDIS_ERROR("400016","主redis操作失败"),
    HTTP_WS_REQUEST_ERROR("400018","请求ws失败"),
    HTTP_RESP_BODY_NULL("400019","ws模块响应包体为空"),
    HTTP_CALL_WS_ERROR("400020","通过ws模块获取信息失败"),
    HTTP_SMS_REQUEST_ERROR("400021","请求sms失败"),
    HTTP_SEND_THREAD_FULL("400022","发送线程池已满"),
    HTTP_ASYNC_THREAD_FULL("400023","异步接收消息线程池已满"),

    /**
     * 账户类错误码
     */
    IP_AUTH_FAIL("401100","请求IP不在白名单内"),
    ACCOUNT_NOT_EXIST("401101","主账户不存在"),
    ACCOUNT_DISABLED("401102","主账户已停用"),
    SUB_ACCOUNT_DISABLE("401103","子账户已停用"),
    SUB_ACCOUNT_NOT_EXIST("401104","子账户不存在"),
    ACCOUNT_AND_SUB_ACCOUNT_NOT_MATCH("401105","主子账户不匹配"),
    BEYOND_ACCOUNT_CONCURRENT("401106","接口请求超过规定的并发数"),
    ACCOUNT_NOT_EXIST_DEFAULT_SUB("401107","主账户未配置默认子账户"),
    DEFAULT_SUB_ACCOUNT_NOT_EXIST("401108","默认子账户不存在"),


    /**
     * 请求头类错误码
     */
    CONTENT_TYPE_IS_NULL("401200","HTTP Content-Type为空"),
    CHUNKED_NOT_SUPPORT("401201","HTTP Chunked 编码不支持"),
    CONTENT_LENGTH_IS_NULL("401202","HTTP Content-Length为空"),
    AUTHORIZATION_IS_NULL("401203","请求包头Authorization参数为空"),
    SIG_IS_NULL("401204","请求地址Sig参数为空"),
    BASE64DECODE_ERROR("401205","请求包头Authorization参数Base64解码失败"),
    AUTH_DECODE_FORMAT_ERROR("401206","请求包头Authorization参数解码后格式有误"),
    AUTH_ACCOUNT_IS_NULL("401207","请求包头Authorization参数解码后账户ID为空白"),
    AUTH_TIMESTAMP_IS_NULL("401208","请求包头Authorization参数解码后时间戳为空白"),
    AUTH_TIMESTAMP_FORMAT_ERROR("401209","请求包头Authorization参数中时间戳格式有误，请参考yyyyMMddHHmmss"),
    AUTH_TIME_EXPIRED("401210","请求包头Authorization参数解码后时间戳过期"),
    AUTH_ACCOUNT_NOT_MATCH("401211","请求包头Authorization参数中账户ID跟请求地址中的账户ID不一致"),


    /**
     * 参数类错误码
     */

    PARAM_NOTIN_LLIST("501300","参数非法"),
    PARAM_VALUE_ERROR("501301","参数值异常"),
    PARAM_NOT_NULL("501302","参数不能为空"),
    PARAM_REPETITION("501303","重复添加"),


    /**
     * 通信
     */
    MAIL_SEND_ERROR("601400","邮件发送失败"),
    MAIL_UPPER_LIMIT("601401","邮件批量发送达到上限"),

    SMS_UPPER_LIMIT("601402","短信批量发送达到上限"),
    SMS_PARAM_FORMAT_ERROR("601403","短信参数格式异常"),



    /**
     * 交易类错误码
     */
    TRADE_CHANNLE_ERROR("701500","渠道异常"),
    TRADE_BUSS_ERROR("701501","业务异常"),
    TRADE_CHANNLE_DELAY_ERROR("701502","需要稍后重新获取结果"),
    TRADE_TRADEID_NOT_EXIT("701503","交易不存在"),


    /**
     * 用户登陆注册，找回密码的错误码
     * @param code
     * @param message
     */
    USER_NOT_EXIST("10100","用户不存在"),
    USER_EXIST("10101","该帐户已存在")
    ;


    GlobalErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;



    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
