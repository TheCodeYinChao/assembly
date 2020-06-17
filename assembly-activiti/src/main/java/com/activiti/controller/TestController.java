package com.activiti.controller;

import com.activiti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2019/9/14.
 */
@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/test",consumes= MimeTypeUtils.APPLICATION_JSON_VALUE)
    public String test(){
        return "测试";
    }

    @GetMapping("/getUser")
    public Object getUser(){
        return userService.getUser();
    }
}
