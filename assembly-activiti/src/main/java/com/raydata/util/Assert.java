package com.raydata.util;

import com.raydata.core.ResultVo;
import com.raydata.core.exception.ArgumentException;
import com.raydata.core.exception.GlobalErrorCode;
import com.raydata.core.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class Assert {

    /**
     * 非空校验
     *
     * @param obj
     * @param desc
     */
    public static void objNotNull(Object obj, String desc) {
        if (obj == null) {
            log.error("[Params Not Null] , 参数 {} Is Not Null", desc);
            throw new ArgumentException(GlobalErrorCode.PARAM_NOT_NULL, String.format("Param %s Not Is Null!!", desc));
        }
        if (obj instanceof String) {
            if (StringUtils.isEmpty((String) obj)) {
                log.error("[Params Not Null] , 参数 {} Is Not Null", desc);
                throw new ArgumentException(GlobalErrorCode.PARAM_NOT_NULL, String.format("Param %s Not Is Null!!", desc));
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0) {
                log.error("[Params Not Null] , 参数 {} Is Not Null", desc);
                throw new ArgumentException(GlobalErrorCode.PARAM_NOT_NULL, String.format("Param %s Not Is Null!!", desc));
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                log.error("[Params Not Null] , 参数 {} Is Not Null", desc);
                throw new ArgumentException(GlobalErrorCode.PARAM_NOT_NULL, String.format("Param %s Not Is Null!!", desc));
            }
        }
    }

    /**
     * webService 接口返回数据校验
     *
     * @param resultVo
     * @param desc
     */
    public static void checkReturnData(ResultVo resultVo, String desc) {
        if (resultVo == null) {
            log.error("[Interface request exception] , 接口 {} Interface request exception", desc);
            throw new HttpException(GlobalErrorCode.HTTP_WS_REQUEST_ERROR, String.format(" 接口 {} Interface request exception", desc));
        }

        if (!GlobalErrorCode.SUCCESS.getCode().equals(resultVo.getCode())) {
            log.error("[Interface return data exception] ,  {} 接口调用失败", desc);
            log.error(resultVo.getMsg());
            throw new HttpException(GlobalErrorCode.HTTP_CALL_WS_ERROR, String.format("%s 接口调用失败", desc));
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code IllegalArgumentException}
     * if the expression evaluates to {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ArgumentException(GlobalErrorCode.PARAM_VALUE_ERROR, message);
        }
    }


    /**
     * 校验金额是否一致
     *
     * @param aAmount
     * @param bAmount
     * @param desc
     */
    public static void validAmount(BigDecimal aAmount, BigDecimal bAmount, String desc) {
        if (aAmount == null || bAmount == null) {
            log.error("ValidAmount Param  [{}] Not null", desc);
            throw new ArgumentException(GlobalErrorCode.PARAM_NOT_NULL, String.format("Param %s Not Is Null!!", desc));
        }
        if (aAmount.compareTo(bAmount) != 0) {
            log.error("ValidAmount Param [{}] Amount Value Error ", desc);
            throw new ArgumentException(GlobalErrorCode.PARAM_VALUE_ERROR, "Param " + desc + " Value Error");
        }
    }


    /**
     * 检验手机号
     *
     * @param phone
     * @param desc
     */
    public static void validPhone(String phone, String desc) {
        objNotNull(phone, desc);
        boolean b = CheckUtils.checkCellphone(phone);
        if (!b) {
            log.error("ValidAmount Param  [{}] Not null", desc);
            throw new ArgumentException(GlobalErrorCode.PARAM_NOT_NULL, String.format("手机号 %s 格式错误!!", desc));
        }
    }

    /**
     * 批量参数不为空校验
     */
    public static void paramsNotNullValid(Object o, String... args) {
        if (o == null || args == null || args.length == 0) {
            return;
        }
        String[] params = args;
        try {
            for (String param : params) {
                Field field = o.getClass().getDeclaredField(param);
                field.setAccessible(true);
                Object o1 = field.get(o);
                objNotNull(o1, param);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
