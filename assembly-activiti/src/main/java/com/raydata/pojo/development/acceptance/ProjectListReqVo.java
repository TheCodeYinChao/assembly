package com.raydata.pojo.development.acceptance;

import com.raydata.pojo.process.PageReqVo;
import lombok.Data;

/**
 * @Author zhouchao
 * @Date 2019/9/11 18:52
 * @Description
 **/
@Data
public class ProjectListReqVo extends PageReqVo {
    //开发经理id
    private String userId;

}
