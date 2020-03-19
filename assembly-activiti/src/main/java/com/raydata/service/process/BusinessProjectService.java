package com.raydata.service.process;// +----------------------------------------------------------------------

import com.raydata.model.BusinessProject;
import com.raydata.pojo.process.BusinessProjectReqVo;
import com.raydata.pojo.process.FormVo;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/11
// +----------------------------------------------------------------------
// | Time: 16:17
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
public interface BusinessProjectService {

    int addProject(BusinessProjectReqVo businessProjectReq);//产品经理新建项目

    int uploadPrototype(FormVo formVo);//产品经理上传原型和报价方案

    int assign(FormVo formVo);//行业负责人指派开发经理

    int check(FormVo formVo);//开发经理审核原型，PMO审核原型

}
