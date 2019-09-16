package com.raydata.pojo.process;

import lombok.Data;

/**
 * @Author zhouchao
 * @Date 2019/9/11 18:52
 * @Description
 **/
@Data
public class PageReqVo {
    private String userId;
    //页码
    private int page;
    //每页显示条数
    private int limit;
}
