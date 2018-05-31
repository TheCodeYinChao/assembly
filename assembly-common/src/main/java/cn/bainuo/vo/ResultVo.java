package cn.bainuo.vo;

import cn.bainuo.exception.exceptionenum.GlobalErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * User: zyc
 * Date: 2018-05-10
 * Time: 10:33
 * Version ：1.0
 * Description: 结果类型
 */
@Data
public class ResultVo implements Serializable {

    private static final long serialVersionUID = -2369183809383216770L;
    private String code;//状态码
    private String msg;//描述信息
    private Object data;//返回的数据


    /**
     * 相应正常
     * @param object
     * @return
     */
    public static ResultVo ok(Object object){
        if (object!=null){
            return  new ResultVo(GlobalErrorCode.SUCCESS.getCode(),
                    GlobalErrorCode.SUCCESS.getMessage(),object);
        }else {
            return  new ResultVo(GlobalErrorCode.SUCCESS.getCode(),
                    GlobalErrorCode.SUCCESS.getMessage(),null);
        }
    }

    /**
     * 相应正常
     * @return
     */
    public static ResultVo ok(){
            return  new ResultVo(GlobalErrorCode.SUCCESS.getCode(),
                    GlobalErrorCode.SUCCESS.getMessage(),null);
    }
    /**
     * 相应正常
     * @param msg
     * @return
     */
    public static ResultVo ok(String msg){
        return  new ResultVo(GlobalErrorCode.SUCCESS.getCode(), msg, null);
    }

    /**
     * 响应异常
     * @param rs 响应状态
     * @return
     */
    public static ResultVo error(GlobalErrorCode rs){
        return  new ResultVo(rs.getCode(),rs.getMessage(),null);
    }

    /**
     * 可填充响应错误信息
     * @param rs 响应状态
     * @param data 响应数据
     * @return
     */
    public static ResultVo error(GlobalErrorCode rs, Object data){
        return  new ResultVo(rs.getCode(),rs.getMessage(),data);
    }

    /**
     * 相应异常 自定义响应信息
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResultVo error(String code, String msg, Object data){
        return new ResultVo(code,msg,data);
    }

    /**
     *参数有误
     */
    public static ResultVo paramError(Object data){
        return new ResultVo(GlobalErrorCode.PARAM_NOT_NULL.getCode(), GlobalErrorCode.PARAM_NOT_NULL.getMessage(),data);
    }

    /**
     * 服务器内部错误
     */
    public static ResultVo serverError(Object data){
        return new ResultVo(GlobalErrorCode.SYSTEM_ERROR.getCode(), GlobalErrorCode.SYSTEM_ERROR.getMessage(),data);
    }

    public ResultVo(GlobalErrorCode rs, Object data) {
        this.code = rs.getCode();
        this.msg = rs.getMessage();
        this.data = data;
    }

    public ResultVo(String code, String msg,Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
