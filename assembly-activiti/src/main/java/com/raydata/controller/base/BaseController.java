package com.raydata.controller.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.raydata.core.page.PagePm;
import com.raydata.util.JacksonUtil;

import java.util.Map;
import java.util.Objects;

public class BaseController {

    protected IPage bulidPage(long current,long size){
        IPage iPage = new PagePm(current,size);
        return iPage;
    }

    protected IPage bulidPage(String param){
        Map<String,Object> map = JacksonUtil.toObj(param, Map.class);
        Object page = map.get("page");
        Object limit = map.get("limit");
        long aLong = 1;
        long aLong1 = 10;
        if(!Objects.isNull(page) && !Objects.isNull(limit)){
             aLong = Long.valueOf(page.toString());
             aLong1 = Long.valueOf(limit.toString());
        }
        IPage iPage = new PagePm(aLong,aLong1);
        return iPage;
    }

    public static void main(String[] args) {
        String str="{\"page\":\"\",\"limit\":\"\"}";
        new BaseController().bulidPage(str);

    }
}
