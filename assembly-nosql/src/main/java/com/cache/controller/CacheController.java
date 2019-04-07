package com.cache.controller;

import cn.bainuo.controller.BaseController;
import com.cache.context.cache.CacheContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2019/4/5.
 */
@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController{

    @RequestMapping("/oper")
    public Object cache(@RequestParam String oper,@RequestParam String dataSource){



        return "";
    }
}
