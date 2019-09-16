package com.raydata.controller.process;// +----------------------------------------------------------------------

import com.raydata.controller.base.BaseController;
import com.raydata.core.ResultVo;
import com.raydata.pojo.process.BusinessProjectReqVo;
import com.raydata.pojo.process.FormVo;
import com.raydata.service.process.BusinessProjectService;
import com.raydata.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/11
// +----------------------------------------------------------------------
// | Time: 16:14
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@RestController
@RequestMapping("/project/confirm")
public class ProjectConfirmController extends BaseController {

    @Autowired
    private BusinessProjectService businessProjectService;

    /**
     * @description: 产品经理新增项目
     *
     * @param:
     * @return: 
     * @author: haiying.qin <haiying.qin@raykite.com>
     * @createTime: 2019/9/12 14:13
     */
    
    @PostMapping("/add")
    public Object addProject(@RequestBody String params){
        BusinessProjectReqVo businessProjectReq = JacksonUtil.toObj(params, BusinessProjectReqVo.class);
        businessProjectService.addProject(businessProjectReq);
        return ResultVo.success();
    }

    /**
     * @description: 产品经理上传原型和报价方案
     *
     * @param:
     * @return: 
     * @author: haiying.qin <haiying.qin@raykite.com>
     * @createTime: 2019/9/16 13:41
     */
    
    @PostMapping("/prototype/upload")
    public Object uploadPrototype(@RequestBody String params){
        FormVo formA = JacksonUtil.toObj(params, FormVo.class);
        businessProjectService.uploadPrototype(formA);
        return ResultVo.success();
    }

    
    /**
     * @description: 行业负责人指派开发经理
     *
     * @param:
     * @return: 
     * @author: haiying.qin <haiying.qin@raykite.com>
     * @createTime: 2019/9/16 14:19
     */
    
    @PostMapping("/assign")
    public Object assign(@RequestBody String params){
        FormVo formA = JacksonUtil.toObj(params, FormVo.class);
        businessProjectService.assign(formA);
        return ResultVo.success();
    }

    /**
     * @description: 开发经理审核原型,PMO审核原型
     *
     * @param: 
     * @return: 
     * @author: haiying.qin <haiying.qin@raykite.com>
     * @createTime: 2019/9/16 14:59
     */
    
    @PostMapping("/check")
    public Object check(@RequestBody String params){
        FormVo formA = JacksonUtil.toObj(params, FormVo.class);
        businessProjectService.check(formA);
        return ResultVo.success();
    }



}
