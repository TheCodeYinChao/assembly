package com.raydata.core;

import com.raydata.core.exception.GlobalErrorCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ResultVo {

    private String code;

    private String msg;

    private Object data;


    private ResultVo(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    /**
     * 成功
     * @return
     */
    public static ResultVo success(){
        return handleRs(GlobalErrorCode.SUCCESS.getCode(),GlobalErrorCode.SUCCESS.getMessage(),null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultVo success(Object data){
        return handleRs(GlobalErrorCode.SUCCESS.getCode(),GlobalErrorCode.SUCCESS.getMessage(),data);
    }

    /**
     * 成功
     * @param msg
     * @param data
     * @return
     */
    public static ResultVo success(String msg,Object data){
        return handleRs(GlobalErrorCode.SUCCESS.getCode(),msg,data);
    }

    /**
     * 失败
     * @param globalErrorCode
     * @param data
     * @return
     */
    public static ResultVo failure(GlobalErrorCode globalErrorCode,Object data){
        return handleRs(globalErrorCode.getCode(),globalErrorCode.getMessage(),data);
    }
    /**
     * 失败
     * @param globalErrorCode
     * @param msg
     * @param data
     * @return
     */
    public static ResultVo failure(GlobalErrorCode globalErrorCode,String msg,Object data){
        String m = "";
        if(StringUtils.isEmpty(msg)){
            m = globalErrorCode.getMessage();
        }else {
            m = msg;
        }
        return handleRs(globalErrorCode.getCode(),m,data);
    }

    /**
     * 失败
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResultVo failure(String code,String msg,Object data){
        return handleRs(code,msg,data);
    }


    private  static ResultVo handleRs(String code,String msg,Object data){
        return new ResultVo(code,msg,data);
    }

}
