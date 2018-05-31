package cn.bainuo.controller;

import cn.bainuo.plugin.ParameterMap;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * User: zyc
 * Date: 2018-05-09
 * Time: 15:24
 * Version ：1.0
 * Description:
 */
@Slf4j
public class BaseController {

    /**
     * 处理请求参数
     * @param param
     * @return
     */
    public ParameterMap jsonParseMap(String param){
        log.info("[base-controller] params{}",param);
        Map<String, String> paramMap = null;
        ParameterMap parameterMap = new ParameterMap();;

        if(StringUtils.isEmpty(param)){
            log.info("[base-controller] params{}" ,"访问参数为空");
            return parameterMap;
        }

        try {
            paramMap = JSONObject.parseObject(param,
                    new TypeReference<Map<String, String>>() {});
           for( Map.Entry m:paramMap.entrySet()){
               parameterMap.put(m.getKey(),m.getValue());
           }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[baseController-jsonParseMap] 参数JSON异常{}",e.getMessage());
        }
        return parameterMap;
    }




}
