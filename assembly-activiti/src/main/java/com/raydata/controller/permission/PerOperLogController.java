package com.raydata.controller.permission;

import com.raydata.controller.base.BaseController;
import com.raydata.service.PerOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perOperLog")
public class PerOperLogController extends BaseController {
    @Autowired
    private PerOperLogService perOperLogService;

    @RequestMapping("/selectPerOperLogs")
    public Object selectPerOperLogs(@RequestBody String param){
        return perOperLogService.selectPerOperLogsPage(param,bulidPage(param));
    }
}
