package cn.bainuo.exception.exceptionenum;

/**
 * 全局错误码
 *
 * @author DHC
 */

public enum GlobalErrorCode implements ErrorCode{

    SUCCESS("000000", "成功"),

    //系统级错误
    SYSTEM_ERROR("400000","系统错误"),
    SYSTEM_BUSY("400001","系统繁忙"),
    HTTP_MASTER_REDIS_ERROR("400016","主redis操作失败"),
    HTTP_WS_REQUEST_ERROR("400018","请求ws失败"),
    HTTP_RESP_BODY_NULL("400019","ws模块响应包体为空"),
    HTTP_CALL_WS_ERROR("400020","通过ws模块获取信息失败"),
    HTTP_SMS_REQUEST_ERROR("400021","请求sms失败"),
    HTTP_SEND_THREAD_FULL("400022","发送线程池已满"),
    HTTP_ASYNC_THREAD_FULL("400023","异步接收消息线程池已满"),

    //请求相关
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),

    //参数
    PARAM_NOT_NULL("40010","Param Not Null"),
    PARAM_NOT_TABLE("40011","Param Not In Table"),
    PARAM_NOT_INSERT_FAIL("40012","Insert Table Fail"),
    PARAM_NOT_UPDATA_FAIL("40013","Update Table Fail"),
    PARAM_FORMAT_EXCEPTION("40014","Param Format Not True"),
    PARAM_NOT_DELETE_FAIL("40015","Delete Table Fail"),
    PARAM_NOT_JSON("40016","Data Format Not Json！"),


    //数量类
    COUNT_IS_MAX("40100","The number upper limit"),

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
    REQUEST_PACKET_NULL("400010","请求包体为空"),
    REQUEST_PARSE_PACKET_ERROR("400015","请求包体格式有误"),

    ACCOUNT_LENGTH_ERROR("401300","主账号长度有误"),
    SUBACCOUNT_LENGTH_ERROR("401301","子账号长度有误"),
    AUTH_SIG_VALIDATE_FAIL("401302","请求地址Sig参数校验失败"),
    MESSAGE_IS_NULL("401303","短信内容为空"),
    MESSAGE_LENGTH_BEYOND("401304","短信内容超长"),
    MOBILE_IS_NULL("401305","发送号码为空"),
    MOBILE_FORMAT_ERROR("401306","号码格式有误"),
    MOBILE_LENGTH_ERROR("401307","号码长度有误"),
    UID_LENGTH_BEYOND("401308","UID超长"),
    CODE_LENGTH_BEYOND("401309","扩展码超长"),
    CODE_FORMAT_ERROR("401310","扩展码格式有误"),
    EXT_LENGTH_BEYOND("401311","扩展参数超长"),
    SMS_LIST_IS_NULL("401312","短信列表为空"),
    MOBILE_REPEAT_ERROR("401313","发送号码重复"),
    MASS_LIST_BEYOND("401314","群发列表超限"),


    //----------------------------TCP 发送模块---------------------------------//
    /**
     * 外部错误
     */
    /**
     * 内部系统错误
     */
    TCP_SYSTEM_ERROR("460000","系统错误"),
    TCP_RECEIVE_BODY_NULL("460010","请求包体为空"),
    TCP_PARAM_FORMAT_ERROR("460011","请求参数格式错误"),
    TCP_PARAM_IS_NULL("460012","请求参数为空"),
    TCP_PARSE_BODY_ERROR("460015","请求包体格式有误"),
    TCP_MASTER_REDIS_ERROR("460016","主redis操作失败"),
    TCP_SLAVE_REDIS_ERROR("460017","从redis操作失败"),
    WS_REQUEST_ERROR("460018","请求ws失败"),
    TCP_RESPONSE_BODY_NULL("460019","请求包体为空"),
    CALL_WS_ERROR("460020","通过ws模块获取信息失败"),

    SOCKET_CONN_FAIL("461100","SOCKET连接失败"),
    CONN_EXIST("461101","连接已存在"),
    CLOSE_CONN_FAILED("461102","连接关闭失败"),
    CONN_UN_INIT("461103","连接未初始化"),
    UN_EFFECT_CONN("461104","无有效连接"),
    PROTOCL_NOT_SUPPORT("461105","不支持的协议"),
    MSG_IN_QUEUE_FAIL("461106","短信入队列失败"),
    CHANNEL_IS_BUSY("461107","通道缓冲队列已满"),
    PUSH_THREAD_IS_FULL("461108","推送线程池已满,无法工作"),
    RECEIVE_THREAD_IS_FULL("461109","接收响应线程池已满,无法工作"),
    RECONN_OPERATION("461110","连接重连"),
    RECONN_OVER_MAX_TIMES("461111","规定次数内仍未重连成功,关闭连接"),
    UN_GET_EFFECT_CHANNEL("461112","未查询到相关通道信息"),
    RETRY_THREE_ERROR("461200","重试三次仍未下发成功"),

    ;





    GlobalErrorCode(String code,String message){
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;



    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
