package com.raydata.pojo.process;// +----------------------------------------------------------------------

import com.raydata.model.BusinessFiles;
import com.raydata.model.BusinessForm;
import lombok.Data;

import java.util.List;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/12
// +----------------------------------------------------------------------
// | Time: 14:18
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Data
public class FormVo {

    private String userId;//操作人

    private String handler;//处理人

    private List<String> users;//知会者IDs

    private List<BusinessFiles> files;//附件

    private BusinessForm form;//表单数据

}
