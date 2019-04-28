package com.cache.controller;

import cn.bainuo.controller.BaseController;
import com.cache.context.cache.CacheContext;
import com.cache.redis.opea.RedisOpera;
import com.cache.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Admin on 2019/4/5.
 */
@RestController
@RequestMapping("/cache")
@Slf4j
public class CacheController extends BaseController{
    @Autowired
    private RedisOpera opera;
    @Autowired
    private LogService logService;

    @RequestMapping("/oper")
    public Object cache(@RequestParam String oper,@RequestParam String dataSource){



        return "";
    }

        @GetMapping("/redis")
    public String redis(){
        opera.insert("zyc-test","this is test of data!");
        return "存储success";
    }

    @GetMapping("/getVal/{key}")
    public String getVal(@PathVariable String key){
        return opera.getVal(key);
    }


    @GetMapping("/logDemo")
    public void logDemo(){
        log.info("链路追钟");
        logService.seachLog();
    }


}
