package com.raydata.controller.permission;

import com.raydata.controller.base.BaseController;
import com.raydata.model.PerPermission;
import com.raydata.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody String param){
        return permissionService.saveUpdate(param);
    }

    @PostMapping("/selectPermission")
    public Object selectPermission(){
        return permissionService.selectAllPer();
    }

    @PostMapping("/selectPermissionPage")
    public Object selectPermissionPage(@RequestBody String param){
        return permissionService.selectPerPage(param,bulidPage(param));
    }
}
