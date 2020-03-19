package com.raydata.controller.permission;

import com.raydata.controller.base.BaseController;
import com.raydata.service.PmDictionarieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pmDictionarie")
public class PmDictionarieController extends BaseController {
    @Autowired
    private PmDictionarieService pmDictionarieService;

/*    @RequestMapping("/inserPmDictionarie")
    public Object inserPmDictionarie(@RequestBody String  param){

        return pmDictionarieService.inserPmDictionarie(param);
    }

    @RequestMapping("/updatePmDictionarie")
    public Object updatePmDictionarie(@RequestBody String param){
        return pmDictionarieService.updatePmDictionarie(param);
    }
    */
    @RequestMapping("/saveOrUpdate")
    public Object saveAndUpdate(@RequestBody String  param){
        return pmDictionarieService.saveAndUpdate(param);
    }


    @RequestMapping("/selectById")
    public Object selectById(@RequestParam Integer id){
        return pmDictionarieService.selectById(id);
    }

    @RequestMapping("/selectPage")
    public Object selectPage(@RequestBody String param){
        return pmDictionarieService.queryPage(param,bulidPage(param));
    }
}
