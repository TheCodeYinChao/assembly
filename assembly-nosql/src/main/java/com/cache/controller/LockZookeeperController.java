package com.cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LockZookeeperController {

    @GetMapping("lockDemo")
    public void lockDemo(){

    }
}
