package com.raydata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.raydata.model.PmDictionaries;

public interface PmDictionarieService  extends IService<PmDictionaries> {
    Object inserPmDictionarie(String pmDictionaries);

    Object updatePmDictionarie(String param);

    Object selectById(Integer id);

    Object queryPage(String param, IPage page);

     Object saveAndUpdate(String param);
}
