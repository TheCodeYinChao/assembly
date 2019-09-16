package com.raydata.controller.permission;

import com.raydata.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/validUserRole")
    public Object validUserRole(@RequestParam Integer userId,@RequestParam Integer roleId){
        return "";
    }

}
