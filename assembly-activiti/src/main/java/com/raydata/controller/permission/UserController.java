package com.raydata.controller.permission;

import com.raydata.controller.base.BaseController;
import com.raydata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personnel")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    @PostMapping("/insertUser")
    public Object inserUser(@RequestBody String param){
        return userService.insertUser(param);
    }

    @PostMapping("/updateUser")
    public Object updateUser(@RequestBody String param){
        return userService.updateUser(param);
    }

    @PostMapping("/queryUserInfoByUserId")
    public Object queryById(@RequestParam Integer userId){
        return userService.getByUserId(userId);
    }


    @PostMapping("/queryUsers")
    public Object getUsers(@RequestBody String param){
        return userService.getUsers(param, bulidPage(param));
    }


    @PostMapping("/selectUserPer")
    public Object selectUserPer(@RequestParam(required = false) Integer userId){
        return userService.selectUserPer(userId);
    }



}
