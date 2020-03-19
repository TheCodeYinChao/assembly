package com.raydata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.raydata.core.ResultVo;
import com.raydata.dao.PmDictionarieMapper;
import com.raydata.model.PmDictionaries;
import com.raydata.service.PmDictionarieService;
import com.raydata.util.Assert;
import com.raydata.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class PmDictionarieServiceImpl extends ServiceImpl<PmDictionarieMapper,PmDictionaries> implements PmDictionarieService {
    @Autowired
    private PmDictionarieMapper pmDictionarieMapper;


    @Override
    @Transactional
    public Object inserPmDictionarie(String param) {
        PmDictionaries pmDictionaries = JacksonUtil.toObj(param, PmDictionaries.class);
        Assert.paramsNotNullValid(pmDictionaries,"type","name");
        pmDictionaries.setCreatetime(new Date());
        pmDictionarieMapper.insert(pmDictionaries);
        return ResultVo.success();
    }

    @Override
    @Transactional
    public Object updatePmDictionarie(String param) {
        PmDictionaries pmDictionaries = JacksonUtil.toObj(param, PmDictionaries.class);
        Assert.objNotNull(pmDictionaries.getId(),"id");
        pmDictionarieMapper.update(pmDictionaries,new UpdateWrapper<PmDictionaries>().eq("id",pmDictionaries.getId()));
        return ResultVo.success();
    }


    @Override
    @Transactional
    public Object saveAndUpdate(String param) {
        PmDictionaries pmDictionaries = JacksonUtil.toObj(param, PmDictionaries.class);
        if(Objects.isNull(pmDictionaries.getId())) {
            Assert.paramsNotNullValid(pmDictionaries,"type","name");
            pmDictionaries.setCreatetime(new Date());
        }
       saveOrUpdate(pmDictionaries);
        return ResultVo.success();
    }


    @Override
    public Object selectById(Integer id) {
        Assert.objNotNull(id,"id");
        PmDictionaries pd = pmDictionarieMapper.selectOne(new QueryWrapper<PmDictionaries>().eq("id", id));
        return ResultVo.success(pd);
    }

    @Override
    public Object queryPage(String param, IPage page) {
        PmDictionaries pmD = JacksonUtil.toObj(param, PmDictionaries.class);
        page = pmDictionarieMapper.selectPage(page,new QueryWrapper<PmDictionaries>()
                                                    .eq(false,"id",pmD.getId())
                                                    .like(false,"name",pmD.getName())
                                                    .eq(false,"type",pmD.getType())
                                                    .eq("status",0)
                                            );

        return ResultVo.success(page);
    }
}
