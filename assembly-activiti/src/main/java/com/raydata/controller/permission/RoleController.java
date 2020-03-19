package com.raydata.controller.permission;

import com.raydata.controller.base.BaseController;
import com.raydata.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/insertRole")
    public Object insertRole(@RequestBody String param){
        return roleService.insertRole(param);
    }

    @PostMapping("/updateRole")
    public Object updateRole(@RequestBody String param){
        return roleService.updateRole(param);
    }

    @PostMapping("/selectRoleById")
    public Object selectRoleById(@RequestParam Integer roleId){
        return roleService.selectRoleById(roleId);
    }


    @PostMapping("/selectRolesPage")
    public Object selectRolesPage(@RequestBody String param){
        return roleService.selectRolesPage(param,bulidPage(param));
    }

}
