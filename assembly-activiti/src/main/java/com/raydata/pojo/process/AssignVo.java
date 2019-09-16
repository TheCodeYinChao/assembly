package com.raydata.pojo.process;// +----------------------------------------------------------------------

import lombok.Data;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/16
// +----------------------------------------------------------------------
// | Time: 14:32
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Data
public class AssignVo {

    private String userId;//操作人

    private String handler;//处理人

    private String taskId;//任务ID
}
