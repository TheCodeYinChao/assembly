package com.raydata.controller.permission;

import com.raydata.model.PerRolePermission;
import com.raydata.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping("/validRolePer")
    public Object validRolePer(@RequestParam Integer roleId,@RequestParam Integer perId){
        return "";
    }
}
